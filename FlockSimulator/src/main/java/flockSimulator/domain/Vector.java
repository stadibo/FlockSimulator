package flockSimulator.domain;

import flockSimulator.util.MathWrapper;

/**
 * Class for creating 2D vectors and calculating using 2D vectors
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
        this.x = 0.0;
        this.y = 0.0;
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

    /**
     * Add another vector to this vector
     *
     * @param other
     */
    public void add(Vector other) {
        this.x = (this.x + other.getX());
        this.y = (this.y + other.getY());
    }

    /**
     * Add two vectors together to get a new vector
     *
     * @param v1
     * @param v2
     * @return
     */
    public static Vector add(Vector v1, Vector v2) {
        return new Vector(v1.getX() + v2.getX(), v1.getY() + v2.getY());
    }

    /**
     * Subtract a vector from this vector
     *
     * @param other
     */
    public void sub(Vector other) {
        this.x = (this.x - other.getX());
        this.y = (this.y - other.getY());
    }

    /**
     * Subtract a vector v2 from vector v1 to get a new vector (pointing from v2
     * to v1)
     *
     * @param v1
     * @param v2
     * @return
     */
    public static Vector sub(Vector v1, Vector v2) {
        return new Vector(v1.getX() - v2.getX(), v1.getY() - v2.getY());
    }

    /**
     * Multiply vector by scalar
     *
     * @param scalar
     */
    public void mult(double scalar) {
        this.x = (this.x * scalar);
        this.y = (this.y * scalar);
    }

    /**
     * Divide vector by scalar
     *
     * @param scalar
     */
    public void div(double scalar) {
        this.x = (this.x / scalar);
        this.y = (this.y / scalar);
    }

    /**
     * Calculate magnitude of vector
     *
     * @return
     */
    public double magnitude() {
        return MathWrapper.sqrt(this.x * this.x + this.y * this.y);
    }

    /**
     * Normalize vector (to a unit vector)
     */
    public void normalize() {
        double mag = this.magnitude();
        if (mag > 0) {
            this.div(mag);
        }
    }

    /**
     * Change the magnitude of vector to specified length
     *
     * @param m
     */
    public void setMagnitude(double m) {
        this.normalize();
        this.mult(m);
    }

    /**
     * Get dot product of this vector and another vector
     *
     * @param other
     * @return
     */
    public double dot(Vector other) {
        return (this.x * other.getX()) + (this.y * other.getY());
    }

    /**
     * Get theta (angle) between x-axle and this vector, also known as polar
     * angle.
     *
     * @return
     */
    public double heading() {
        return Math.atan2(this.y, this.x);
    }

    /**
     * Limit a vector to a specific magnitude. Variation on setMagnitude()
     *
     * @param max
     */
    public void limit(double max) {
        if (this.magnitude() > max) {
            this.setMagnitude(max);
        }
    }

    /**
     * DISTANCE BETWEEN THIS POINT AND ANOTHER POINT
     *
     * @param other
     * @return
     */
    public double distance(Vector other) {
        return MathWrapper.sqrt(MathWrapper.pow(other.getX() - this.x, 2) + MathWrapper.pow(other.getY() - this.y, 2));
    }

}
