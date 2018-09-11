/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flockSimulator.domain;

import static flockSimulator.gui.simulatorUi.HEIGHT;
import static flockSimulator.gui.simulatorUi.WIDTH;
import java.lang.Math;

/**
 * Based on paper by Craig W. Reynolds http://www.red3d.com/cwr/steer/gdc99/
 *
 * @author peje
 */
public class Agent {

    private Vector position;
    private Vector velocity;
    private Vector acceleration;
    private double maxSpeed;
    private double maxForce;
    private double slowingDistance;

    public Agent(double x, double y) {
        this.position = new Vector(x, y);
        this.velocity = new Vector(0, 0);
        this.acceleration = new Vector(0, 0);

        this.slowingDistance = 300;
        this.maxSpeed = 3;
        this.maxForce = 0.1;
    }
    
// DISTANCE BETWEEN TWO POINTS
//    public double distanceToPoint(double toX, double toY) {
//        return Math.sqrt(Math.pow(toX - this.getX(), 2) + Math.pow(toY - this.getY(), 2));
//    }

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

    public void seek(Vector target) {
        Vector desired = new Vector().sub(target, this.position);
        desired.normalize();
        desired.mult(this.maxSpeed);

        Vector correctionForce = new Vector().sub(desired, this.velocity);
        correctionForce.limit(this.maxForce);

        this.applyForce(correctionForce);
    }

    public void flee(Vector avoid) {
        Vector desired = new Vector().sub(this.position, avoid);
        desired.normalize();
        desired.mult(this.maxSpeed);

        Vector correctionForce = new Vector().sub(desired, this.velocity);
        correctionForce.limit(this.maxForce);

        this.applyForce(correctionForce);
    }

    public void arrive(Vector target) {
        Vector desired = new Vector().sub(target, this.position);
        double distance = desired.magnitude();
        
        // Reynolds way to calculate slowingSpeed
        double rampedSpeed = this.maxSpeed * (distance / this.slowingDistance);
        double clippedSpeed = Math.min(rampedSpeed, this.maxSpeed);
        
        // Set magnitude
        desired.setMagnitude(clippedSpeed);

        Vector correctionForce = new Vector().sub(desired, this.velocity);
        correctionForce.limit(this.maxForce);

        this.applyForce(correctionForce);
    }
    
    public void updatePosition() {
        this.velocity.add(this.acceleration);
        this.velocity.limit(maxSpeed);
        this.position.add(this.velocity);
        this.acceleration.mult(0);
    }

    private void applyForce(Vector force) {
        this.acceleration.add(force);
    }

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

}
