package flockSimulator.domain;

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

    private int width;
    private int height;

    private double r;   // Size of agent / collision radius
    private double awareness; // How far the agent can see
    private double maxSpeed;    // Maximum speed
    private double maxForce;    // Maximum steering force

    private double alignment;   // Modifier
    private double separation;  // Modifier
    private double cohesion;    // Modifier

    public Agent(double x, double y, double size, double awareness, double maxSpeed, double maxForce, int w, int h) {
        this.position = new Vector(x, y);
        this.velocity = new Vector(0, 0);
        this.acceleration = new Vector(0, 0);

        this.poly = new Polygon(-5.0, -5.0, 10.0, 0.0, -5.0, 5.0);
//        this.poly = new Ellipse(5.0, 5.0);

        this.r = size;
        this.awareness = awareness;
        this.maxSpeed = maxSpeed;
        this.maxForce = maxForce;

        this.width = w;
        this.height = h;

        this.alignment = 1.0;
        this.separation = 1.5;
        this.cohesion = 1.0;
    }

    /**
     * Method to calculate and apply a correcting steering force towards a
     * target point correction = desired force minus velocity
     *
     * @param target
     * @return force vector to be applied to agents velocity
     */
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

    /**
     * Method to calculate and apply a correcting steering force away from a
     * target point correction = desired force minus velocity
     *
     * @param target
     * @return force vector to be applied to agents velocity
     */
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

    /**
     * Method for creating a force by which to repel from other agents based on
     * their distance if they are within the view radius of this agent
     *
     * @param agents
     * @return force vector to be applied to agents velocity
     */
    public Vector separation(ArrayList<Agent> agents) {
        double desiredSeparation = this.r * 2;
        Vector sum = new Vector();
        int counter = 0;
        for (Agent other : agents) {
            double dist = this.position.distance(other.getPosition());
            if ((dist > 0) && (dist < desiredSeparation)) {
                Vector offset = new Vector().sub(this.position, other.getPosition());
                offset.normalize();
                offset.div(dist);   // Smaller distance, larger force
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

    /**
     * Method for creating average velocity of other agents within view radius
     * of this agent
     *
     * @param agents
     * @return force vector to be applied to agents velocity
     */
    public Vector alignment(ArrayList<Agent> agents) {
        Vector sum = new Vector();  // sum of velocities
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

    /**
     * Method for creating a force to be attracted to center of flock made of
     * other agents if they are within the view radius of this agent
     *
     * @param agents
     * @return force vector to be applied to agents velocity
     */
    public Vector cohesion(ArrayList<Agent> agents) {
        Vector sum = new Vector();  // sum of positions
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

    /**
     * Method to set acceleration to be applied to velocity
     *
     * @param force vector to be applied to agents velocity
     */
    public void applyForce(Vector force) {
        // mass could be implemented here as a variable a = F / m
        this.acceleration.add(force);
    }

    /**
     * Combine behaviors
     *
     * @param agents list of other agents which are neighbors to this agent
     * @param target a point to maybe use for seeking or fleeing
     */
    public void applyBehaviors(ArrayList<Agent> agents, Vector target) {
        this.flock(agents);
    }

    /**
     * Create flocking behavior
     *
     * @param agents list of other agents which are neighbors to this agent
     */
    public void flock(ArrayList<Agent> agents) {
        Vector separate = this.separation(agents);
        Vector align = this.alignment(agents);
        Vector cohese = this.cohesion(agents);

        separate.mult(this.separation);
        align.mult(this.alignment);
        cohese.mult(this.cohesion);

        applyForce(separate);
        applyForce(align);
        applyForce(cohese);
    }

    /**
     * Method to update position
     */
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
    }

    /**
     * ROTATE POLYGON, too processing heavy for now, need to find a way to
     * rotate more efficently
     */
    public void updateRotation() {
        double angle = this.velocity.heading();
        this.poly.setRotate(Math.toDegrees(angle));
    }

    /**
     * Wraparound: checks if agent is out of the set bounds and set position so
     * that the agent wraps around to the opposite end of the bound which it
     * crossed
     */
    public void checkEdges() {
        if (this.position.getX() > this.width) {
            this.position.setX(0);
        } else if (this.position.getX() < 0) {
            this.position.setX(this.width);
        }

        if (this.position.getY() > this.height) {
            this.position.setY(0);
        } else if (this.position.getY() < 0) {
            this.position.setY(this.height);
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

    public void setPosition(Vector v) {
        this.position = v;
    }

    public Vector getPosition() {
        return this.position;
    }

    public Vector getVelocity() {
        return this.velocity;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setMaxForce(double maxForce) {
        this.maxForce = maxForce;
    }

    public void setAlignment(double alignment) {
        this.alignment = alignment;
    }

    public void setSeparation(double separation) {
        this.separation = separation;
    }

    public void setCohesion(double cohesion) {
        this.cohesion = cohesion;
    }

    // Return how the agent looks (Node object)
    public Node display() {
        return this.poly;
    }

}
