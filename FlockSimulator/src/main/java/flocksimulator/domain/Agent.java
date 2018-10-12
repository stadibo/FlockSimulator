package flocksimulator.domain;

import flocksimulator.util.FlockList;
import javafx.scene.Node;

/**
 * Class implementing "boids" model from paper by Craig W. Reynolds
 * http://www.red3d.com/cwr/steer/gdc99/ Independent agents with specified
 * behaviors can be put in a system to model even more complex behaviors.
 *
 * @author peje
 */
public abstract class Agent {

    protected Vector position;
    protected Vector velocity;
    protected Vector acceleration;
    protected Node poly;

    private int width;
    private int height;

    protected double size;   // Size of agent / collision radius
    protected double awareness; // How far the agent can see
    protected double maxSpeed;    // Maximum speed
    protected double maxForce;    // Maximum steering force

    public Agent(double x, double y, double size, double awareness, double maxSpeed, double maxForce, int w, int h) {
        this.position = new Vector(x, y);
        this.velocity = new Vector(0, 0);
        this.acceleration = new Vector(0, 0);

        this.size = size;
        this.awareness = awareness;
        this.maxSpeed = maxSpeed;
        this.maxForce = maxForce;

        this.width = w;
        this.height = h;
    }

    /**
     * Method to calculate and apply a correcting steering force towards a
     * target point: correction = desired minus velocity
     *
     * @param target
     * @return force vector to be applied to agents velocity
     */
    protected Vector seek(Vector target) {
        // Vector pointing from position towards target
        Vector desired = new Vector().sub(target, this.position);

        // Normalize and scale to maximum speed
        desired.normalize();
        desired.mult(this.maxSpeed);

        // correction = desired minus velocity
        Vector correctionForce = new Vector().sub(desired, this.velocity);
        correctionForce.limit(this.maxForce); //Limit maximum force to be applied

        return correctionForce;
    }

    /**
     * Method to calculate and apply a correcting steering force away from a
     * target point correction = desired force minus velocity
     *
     * @param target
     * @return force vector to be applied to agents velocity
     */
    protected Vector flee(Vector target) {
        // Opposite of seek(): Vector pointing from target towards position
        Vector desired = new Vector().sub(this.position, target);

        // Normalize and scale to maximum speed
        desired.normalize();
        desired.mult(this.maxSpeed);

        // correction = desired minus velocity
        Vector correctionForce = new Vector().sub(desired, this.velocity);
        correctionForce.limit(this.maxForce);

        return correctionForce;
    }

    /**
     * Method to set acceleration to be applied to velocity
     *
     * @param force vector to be applied to agents velocity
     */
    protected void applyForce(Vector force) {
        // mass could be implemented here as a variable a = F / m
        this.acceleration.add(force);
    }

    /**
     * Combine behaviors to create more complex behavior
     *
     * @param agents list of other agents which are neighbors to this agent
     * @param target a point to maybe use for seeking or fleeing
     */
    public abstract void applyBehaviors(FlockList<Agent> agents, Vector target);

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

        // Check boundaries
        this.checkEdges();

        // Update position of polygon in render
        this.poly.setTranslateX(this.position.getX());
        this.poly.setTranslateY(this.position.getY());
    }

    /**
     * Rotate polygon. Is very CPU intensive with many agents on screen.
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
    private void checkEdges() {
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

    /**
     * Provides the visual representation of the agent
     *
     * @return polygon shape for object
     */
    public abstract Node display();

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public abstract void setAlignment(double alignment);

    public abstract void setSeparation(double separation);

    public abstract void setCohesion(double cohesion);

}
