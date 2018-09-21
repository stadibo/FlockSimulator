/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flockSimulator.domain;

import flockSimulator.domain.Vector;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peje
 */
public class VectorTest {

    private Vector v0;
    private Vector v1;
    private Vector v2;
    private Vector v3;

    @Before
    public void setUp() {
        this.v0 = new Vector(0.0, 0.0);
        this.v1 = new Vector(2.0, 1.0);
        this.v2 = new Vector(0.0, 2.0);
        this.v3 = new Vector(3.0, 4.0);
    }
    
    @Test
    public void addingOtherVectorToVector() {
        v0.add(v1);
        assertEquals("adds vector X wrong", 2.0, v0.getX(), 0.01);
        assertEquals("adds vector Y wrong", 1.0, v0.getY(), 0.01);
    }
    
    @Test
    public void addingTwoVectorsReturnsCorrectVector() {
        Vector test1 = new Vector().add(v1, v2);
        assertEquals("adds vector X wrong", 2.0, test1.getX(), 0.01);
        assertEquals("adds vector Y wrong", 3.0, test1.getY(), 0.01);
    }
    
    @Test
    public void subtractingOtherVectorFromVector() {
        this.v0.sub(this.v1);
        assertEquals("difference X value false", -2.0, this.v0.getX(), 0.01);
        assertEquals("difference Y value false", -1.0, this.v0.getY(), 0.01);
    }
    
    @Test
    public void differenceBetweenTwoVectorsReturnsCorrectVector() {
        Vector test2 = new Vector().sub(v1, v2);
        assertEquals("difference X value false", 2.0, test2.getX(), 0.01);
        assertEquals("difference Y value false", -1.0, test2.getY(), 0.01);
    }
    
    @Test
    public void multiplyingScalarToVector() {
        this.v1.mult(5.0);
        assertEquals("multiplies scalar with X wrong", 10.0, this.v1.getX(), 0.01);
        assertEquals("multiplies scalar with Y wrong", 5.0, this.v1.getY(), 0.01);
    }
    
    @Test
    public void dividingVectorByScalar() {
        this.v1.div(4);
        assertEquals("divides X by scalar wrong", 0.5, this.v1.getX(), 0.01);
        assertEquals("divides X by scalar wrong", 0.25, this.v1.getY(), 0.01);
    }
    
    @Test
    public void magnitudeOfVectorCorrect() {
        double mag = this.v3.magnitude();
        assertEquals("calculates magnitude wrong", 5.0, mag, 0.001);
    }
    
    @Test
    public void normalizeVectorCorrect() {
        this.v3.normalize();
        double mag = this.v3.magnitude();
        assertEquals("normalized vector magnitude is " + mag + ", but should be 1.0" , 1.0, mag, 0.001);
        assertEquals(0.6, this.v3.getX(), 0.001);
        assertEquals(0.8, this.v3.getY(), 0.001);
    }
    
    @Test
    public void setsMagnitudeForVectorCorrect() {
        this.v3.setMagnitude(10.0);
        double mag = this.v3.magnitude();
        assertEquals("vector magnitude is " + mag + ", but should be 10.0" , 10.0, mag, 0.001);
        assertEquals(6, this.v3.getX(), 0.001);
        assertEquals(8, this.v3.getY(), 0.001);
    }
    
    @Test
    public void dotProductForTwoVectorsCorrect() {
        double dot = this.v1.dot(v3);
        assertEquals(10.0, dot, 0.001);
    }
    
    @Test
    public void headingForVectorCorrect() {
        double heading = this.v3.heading();
        assertEquals(0.9273, heading, 0.001);
    }
    
    @Test
    public void limitingVectorReturnsCorrectMagnitude() {
        this.v3.limit(3);
        double mag = this.v3.magnitude();
        assertEquals("vector magnitude is " + mag + ", but should be 3.0" , 3.0, mag, 0.001);
        assertEquals(1.8, this.v3.getX(), 0.001);
        assertEquals(2.4, this.v3.getY(), 0.001);
    }
    
    @Test
    public void distanceBetweenPointsReturnsCorrect() {
        double distanceBetween = this.v1.distance(this.v3);
        assertEquals(3.16227766, distanceBetween, 0.0001);
    }
}
