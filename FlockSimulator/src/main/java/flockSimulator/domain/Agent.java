/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flockSimulator.domain;

import static flockSimulator.gui.simulatorUi.HEIGHT;
import static flockSimulator.gui.simulatorUi.WIDTH;
import java.lang.Math;
import javafx.scene.Node;
import javafx.scene.shape.Polygon;

/**
 * Based "boids" model from paper by Craig W. Reynolds http://www.red3d.com/cwr/steer/gdc99/
 *
 * @author peje
 */
public class Agent {

    private Vector position;
    private Vector velocity;
    private Vector acceleration;
    private Polygon poly;
    
    private double maxSpeed;    // Maximum speed
    private double maxForce;    // Maximum steering force
    private double slowingDistance; // Radius around target in which maxSpeed is scaled
    private double wanderTheta; // Direction for wandering 

    public Agent(double x, double y) {
        this.position = new Vector(x, y);
        this.velocity = new Vector(0, 0);
        this.acceleration = new Vector(0, 0);

        this.poly = new Polygon(-5.0, -5.0, 10.0, 0.0, -5.0, 5.0);

        this.slowingDistance = 200;
        this.maxSpeed = 4;
        this.maxForce = 0.1;
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
    public void seek(Vector target) {
        // Vector pointing from position towards target
        Vector desired = new Vector().sub(target, this.position);
        
        // Normalize and scale to maximum speed
        desired.normalize();
        desired.mult(this.maxSpeed);
        
        // correction = desired force minus velocity
        Vector correctionForce = new Vector().sub(desired, this.velocity);
        correctionForce.limit(this.maxForce); //Limit maximum force to be applied

        this.applyForce(correctionForce);
    }

    // Method to calculate and apply a correcting steering force away from a target point
    // correction = desired force minus velocity
    public void flee(Vector avoid) {
        // Opposite of seek(): Vector pointing from target towards position
        Vector desired = new Vector().sub(this.position, avoid);
        
        // Normalize and scale to maximum speed
        desired.normalize();
        desired.mult(this.maxSpeed);

        // correction = desired force minus velocity
        Vector correctionForce = new Vector().sub(desired, this.velocity);
        correctionForce.limit(this.maxForce);

        this.applyForce(correctionForce);
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
    public void wander() {
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

        this.seek(target);
    }
    
    // Sets acceleration to be applied to velocity
    private void applyForce(Vector force) {
        // mass could be implemented here as a variable a = F / m
        this.acceleration.add(force);
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

    // Return how the agent looks (Node object)
    public Node display() {
        return this.poly;
    }
}
