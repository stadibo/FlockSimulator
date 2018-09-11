/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flockSimulator.domain;

import static flockSimulator.gui.simulatorUi.HEIGHT;
import static flockSimulator.gui.simulatorUi.WIDTH;

/**
 *
 * @author peje
 */
public class Agent {
    
    private Vector location; 
    private Vector velocity;
    private Vector acceleration;
    private double maxVelocity;
    private double maxForce;
    private int spaceWidth;
    private int spaceHeigth;
    
    public Agent(int x, int y) {
        this.location = new Vector(x,y);
        this.velocity = new Vector(0,0);
        this.acceleration = new Vector(0,0);
        
        this.maxVelocity = 5;
        this.maxForce = 0.2;
    }
    
    public int distanceToPoint(int toX, int toY) {
        return 0;
    }
    
//    public void rotate(double direction, double weight) {
//        
//    }
    
    public void goTo(Vector goal) {
        Vector wanted = new Vector().sub(goal, this.location);
        wanted.normalize();
        wanted.mult(maxVelocity);
        
        Vector correctionForce = new Vector().sub(wanted, this.velocity);
        correctionForce.limit(maxForce);
        
        this.applyForce(correctionForce);
    }
    
    public void updatePosition() {
        this.velocity.add(this.acceleration);
        this.velocity.limit(maxVelocity);
        this.location.add(this.velocity);
        this.acceleration.mult(0);
    }
    
    private void applyForce(Vector force) {
        this.acceleration.add(force);
    }
    
    public void checkEdges() {
        if (this.location.getX() > WIDTH) {
            this.location.setX(0);
        } else if (this.location.getX() < 0) {
            this.location.setX(WIDTH);
        }
        
        if (this.location.getY() > HEIGHT) {
            this.location.setY(0);
        } else if (this.location.getY() < 0) {
            this.location.setY(HEIGHT);
        }
    }
    
    public double getX() {
        return this.location.getX();
    }
    
    public double getY() {
        return this.location.getY();
    }
    
}
