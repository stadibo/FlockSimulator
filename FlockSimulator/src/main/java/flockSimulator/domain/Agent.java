/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flockSimulator.domain;

import static flockSimulator.gui.simulatorUi.HEIGHT;
import static flockSimulator.gui.simulatorUi.WIDTH;
import java.lang.Math;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;

/**
 * Class implementing "boids" model from paper by Craig W. Reynolds
 * http://www.red3d.com/cwr/steer/gdc99/ Independent agents with specified
 * behaviors can be put in a system to model even more complex behaviors
 *
 * @author peje
 */
public class Agent {

    private Vector position;
    private Vector velocity;
    private Vector acceleration;
    private Node poly;
    private double r;   // Size of agent / collision radius
    private double awareness; // How far the agent can see

    private double maxSpeed;    // Maximum speed
    private double maxForce;    // Maximum steering force
    private double slowingDistance; // Radius around target within which maxSpeed is scaled
    private double wanderTheta; // Direction for wandering 

    public Agent(double x, double y) {
        this.position = new Vector(x, y);
        this.velocity = new Vector(0, 0);
        this.acceleration = new Vector(0, 0);

        //this.poly = new Polygon(-5.0, -5.0, 10.0, 0.0, -5.0, 5.0);
        this.poly = new Ellipse(5.0, 5.0);
        
        this.r = 12;
        this.awareness = 100;
        this.slowingDistance = 200;
        this.maxSpeed = 4;
        this.maxForce = 0.2;
    }

// https://math.stackexchange.com/questions/377169/calculating-a-value-inside-one-range-to-a-value-of-another-range
// MAP VALUE IN RANGE [a,b] TO OTHER RANGE [c,d]
//    public double affineMap(double x, double a, double b, double c, double d) {
//        if (b - a == 0) {
//            return 0;
//        } else {
//            double y = (x - a) * ((d - c) / (b - a)) + c;
//            return y;
//        }
//    }
    // Method to calculate and apply a correcting steering force towards a target point
    // correction = desired force minus velocity
    public Vector seek(Vector target) {
        // Vector pointing from position towards target
        Vector desired = new Vector().sub(target, this.position);

        // Normalize and scale to maximum speed
        desired.normalize();
        desired.mult(this.maxSpeed);

        // correction = desired force minus velocity
        Vector correctionForce = new Vector().sub(desired, this.velocity);
        correctionForce.limit(this.maxForce); //Limit maximum force to be applied

        return correctionForce;
        //this.applyForce(correctionForce);
    }

    // Method to calculate and apply a correcting steering force away from a target point
    // correction = desired force minus velocity
    public Vector flee(Vector target) {
        // Opposite of seek(): Vector pointing from target towards position
        Vector desired = new Vector().sub(this.position, target);

        // Normalize and scale to maximum speed
        desired.normalize();
        desired.mult(this.maxSpeed);

        // correction = desired force minus velocity
        Vector correctionForce = new Vector().sub(desired, this.velocity);
        correctionForce.limit(this.maxForce);

        return correctionForce;
    }

    // Method to calculate and apply a correcting steering force towards a target point
    // and scale its speed by how close it it from the target, in the end stopping if at target
    // correction = desired force minus velocity
    public void arrive(Vector target) {
        // Distance to target
        Vector desired = new Vector().sub(target, this.position);
        double distance = desired.magnitude();

        // Reynolds way to calculate slowingSpeed
        double rampedSpeed = this.maxSpeed * (distance / this.slowingDistance);
        double clippedSpeed = Math.min(rampedSpeed, this.maxSpeed);

        // Set magnitude = desired speed
        desired.setMagnitude(clippedSpeed);

        // correction = desired force minus velocity
        Vector correctionForce = new Vector().sub(desired, this.velocity);
        correctionForce.limit(this.maxForce);

        this.applyForce(correctionForce);
    }

    // Method to create a wander circle in front of agent on which a random point is created to be 
    // the target of seek() making the agent wander randomly
    public Vector wander() {
        double wanderR = 30;    // Radius for "wandering strength circle"
        double wanderD = 60;   // Circle distance from wanderer
        double change = 0.4;

        //Randomly change wanderTheta for pseudorandom choice of direction
        this.wanderTheta = Math.round(Math.random() * 2 * change) - change;

        // Calculate the position on the wander circle to which to steer towards
        Vector circlePosition = new Vector(this.velocity.getX(), this.velocity.getY());
        circlePosition.setMagnitude(wanderD);
        circlePosition.add(this.position);  // relative to this agents position

        double head = this.velocity.heading(); // Heading to offset with wanderTheta

        Vector circleOffset = new Vector(wanderR * Math.cos(this.wanderTheta + head), wanderR * Math.sin(this.wanderTheta + head));
        Vector target = new Vector().add(circlePosition, circleOffset);

        return this.seek(target);
    }

    public Vector separation(ArrayList<Agent> agents) {
        double desiredSeparation = this.r * 2;
        Vector sum = new Vector();
        int counter = 0;
        for (Agent other : agents) {
            double dist = this.position.distance(other.getPosition());
            if ((dist > 0) && (dist < desiredSeparation)) {
                Vector offset = new Vector().sub(this.position, other.getPosition());
                offset.normalize();
                offset.div(dist);
                sum.add(offset);
                counter++;
            }
        }

        Vector correctionForce = new Vector();
        if (counter > 0) {
            sum.setMagnitude(this.maxSpeed);
            
            // Implement Reynolds: Steering = Desired - Velocity
            correctionForce = new Vector().sub(sum, this.velocity);
            correctionForce.limit(this.maxForce);
        }
        return correctionForce;
    }
    
    private Vector alignment(ArrayList<Agent> agents) {
        Vector sum = new Vector();
        int counter = 0;
        for (Agent other : agents) {
            double dist = this.position.distance(other.getPosition());
            if ((dist > 0) && (dist < this.awareness)) {
                sum.add(other.getVelocity());
                counter++;
            }
        }

        Vector correctionForce = new Vector();
        if (counter > 0) {
            sum.setMagnitude(this.maxSpeed);
            
            // Implement Reynolds: Steering = Desired - Velocity
            correctionForce = new Vector().sub(sum, this.velocity);
            correctionForce.limit(this.maxForce);
        }
        
        return correctionForce;
    }
    
    private Vector cohesion(ArrayList<Agent> agents) {
        Vector sum = new Vector();
        int counter = 0;
        for (Agent other : agents) {
            double dist = this.position.distance(other.getPosition());
            if ((dist > 0) && (dist < this.awareness)) {
                sum.add(other.getPosition());
                counter++;
            }
        }
        
        if (counter > 0) {
            sum.div(counter);
            return this.seek(sum);
        }
        
        return new Vector();
    }

    // Sets acceleration to be applied to velocity
    private void applyForce(Vector force) {
        // mass could be implemented here as a variable a = F / m
        this.acceleration.add(force);
    }
    
    // Combine behaviors
    public void applyBehaviors(ArrayList<Agent> agents, Vector target) {
        this.flock(agents);
        
//        Vector separate = this.separation(agents);
//        Vector wander = this.wander();
          Vector seek = this.seek(target);
//        
//        separate.mult(1.5);
          seek.mult(0.5);
//        wander.mult(0.5);
//        
//        this.applyForce(separate);
          this.applyForce(seek);
//        this.applyForce(wander);
    }
    
    // Create flocking behavior
    public void flock(ArrayList<Agent> agents) {
        Vector separate = this.separation(agents);
        Vector align = this.alignment(agents);
        Vector cohese = this.cohesion(agents);
        
        separate.mult(1.5);
        align.mult(1.0);
        cohese.mult(1.0);
        
        applyForce(separate);
        applyForce(align);
        applyForce(cohese);
    }

    // Method to update position
    public void updatePosition() {
        // Update velocity
        this.velocity.add(this.acceleration);
        // Limit speed
        this.velocity.limit(maxSpeed);
        this.position.add(this.velocity);
        // Reset acceleration to 0 each time
        this.acceleration.mult(0);

        // Update position of polygon in render
        this.poly.setTranslateX(this.position.getX());
        this.poly.setTranslateY(this.position.getY());

        // ROTATE POLYGON, too processing heavy for now, need to find a way to rotate more efficently
//        double angle = this.velocity.heading();
//        this.poly.setRotate(Math.toDegrees(angle));
    }

    // Wraparound
    public void checkEdges() {
        if (this.position.getX() > WIDTH) {
            this.position.setX(0);
        } else if (this.position.getX() < 0) {
            this.position.setX(WIDTH);
        }

        if (this.position.getY() > HEIGHT) {
            this.position.setY(0);
        } else if (this.position.getY() < 0) {
            this.position.setY(HEIGHT);
        }
    }

    public double getX() {
        return this.position.getX();
    }

    public double getY() {
        return this.position.getY();
    }

    public void setVelocity(Vector v) {
        this.velocity = v;
    }

    public Vector getPosition() {
        return this.position;
    }
    
    public Vector getVelocity() {
        return this.velocity;
    }

    // Return how the agent looks (Node object)
    public Node display() {
        return this.poly;
    }
    
}

// Marginally faster than separately looping through all agents for each behavior, try something else

//    public void efficientFlock(ArrayList<Agent> agents) {
//        double desiredSeparation = this.r * 2;
//        Vector sepSum = new Vector();
//        Vector aligSum = new Vector();
//        Vector cohSum = new Vector();
//        
//        int counter = 0;
//        int cohCounter = 0;
//        
//        for (Agent other : agents) {
//            double dist = this.position.distance(other.getPosition());
//            //Separation
//            if ((dist > 0) && (dist < desiredSeparation)) {
//                Vector offset = new Vector().sub(this.position, other.getPosition());
//                offset.normalize();
//                offset.div(dist);
//                sepSum.add(offset);
//                counter++;
//            }
//            if ((dist > 0) && (dist < this.slowingDistance)) {
//                aligSum.add(other.getVelocity());
//                
//                cohSum.add(other.getPosition());
//                counter++;
//                cohCounter++;
//            }
//        }
//        
//        if (counter > 0) {
//            sepSum.setMagnitude(this.maxSpeed);
//            
//            // Implement Reynolds: Steering = Desired - Velocity
//            Vector sepForce = new Vector().sub(sepSum, this.velocity);
//            sepForce.limit(this.maxForce);
//            
//            aligSum.setMagnitude(this.maxSpeed);
//            Vector aligForce = new Vector().sub(aligSum, this.velocity);
//            aligForce.limit(this.maxForce);
//            
//            cohSum.div(cohCounter);
//            Vector cohForce = seek(cohSum);
//            
//            // Combine forces
//            
//            sepForce.mult(1.5);
//            aligForce.mult(1.0);
//            cohForce.mult(1.0);
//            
//            this.applyForce(sepForce);
//            this.applyForce(aligForce);
//            this.applyForce(cohForce);
//        }
//        
//    }