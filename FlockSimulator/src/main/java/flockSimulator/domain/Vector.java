/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flockSimulator.domain;

import java.lang.Math;

/**
 *
 * @author peje
 */
public class Vector {

    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void add(Vector other) {
        this.setX(this.x + other.getX());
        this.setY(this.y + other.getY());
    }

    public static Vector add(Vector v1, Vector v2) {
        return new Vector(v1.getX() + v2.getX(), v1.getY() + v2.getY());
    }

    public void sub(Vector other) {
        this.setX(this.x - other.getX());
        this.setY(this.y - other.getY());
    }

    public static Vector sub(Vector v1, Vector v2) {
        return new Vector(v1.getX() - v2.getX(), v1.getY() - v2.getY());
    }

    public void mult(double scalar) {
        this.setX(this.x * scalar);
        this.setY(this.y * scalar);
    }

    public void div(double scalar) {
        this.setX(this.x / scalar);
        this.setY(this.y / scalar);
    }

    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public void normalize() {
        double mag = this.magnitude();
        if (mag > 0) {
            this.div(mag);
        }
    }

    public void setMagnitude(double m) {
        this.normalize();
        this.mult(m);
    }
    
    public double dot(Vector other) {
        return (this.x * other.getX()) + (this.y * other.getY());
    }
    
    public double heading() {
        return  Math.atan2(this.y, this.x);
    }

    public void limit(double max) {
        if (this.magnitude() > max) {
            this.setMagnitude(max);
        }
    }
    
    // DISTANCE BETWEEN THIS POINT AND ANOTHER POINT
    public double distance(Vector other) {
        return Math.sqrt(Math.pow(other.getX() - this.x, 2) + Math.pow(other.getY() - this.y, 2));
    }
    
}