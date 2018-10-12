/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flocksimulator.util;

import flocksimulator.util.MathWrapper;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peje
 */
public class MathWrapperTest {

    public MathWrapperTest() {
    }

    @Test
    public void sinWorks() {
        double angle1 = 48;
        double angle2 = -135;

        double rad1 = Math.toRadians(angle1);
        double rad2 = Math.toRadians(angle2);

        assertEquals("Sine value is wrong", 0.7431448254773942, MathWrapper.sin(rad1), 0.000000001);
        assertEquals("Sine value is wrong", -0.7071067811865476, MathWrapper.sin(rad2), 0.000000001);
    }

    @Test
    public void cosWorks() {
        double angle1 = 48;
        double angle2 = -135;

        double rad1 = Math.toRadians(angle1);
        double rad2 = Math.toRadians(angle2);

        assertEquals("Cosine value is wrong", 0.6691306063588582, MathWrapper.cos(rad1), 0.000000001);
        assertEquals("Cosine value is wrong", -0.7071067811865476, MathWrapper.cos(rad2), 0.000000001);
    }

    @Test
    public void atan2Works() {
        assertEquals("Atan2 value is wrong", 0.24497866312686414, MathWrapper.atan2(1, 4), 0.000000001);
        assertEquals("Atan2 value is wrong", 2.0344439357957027, MathWrapper.atan2(8, -4), 0.000000001);
    }

    @Test
    public void roundingWorks() {
        assertEquals("Rounded value is wrong", 6, MathWrapper.round(5.51883694824), 0.000000001);
        assertEquals("Rounded value is wrong", -1, MathWrapper.round(-1.454443453345), 0.000000001);
        assertEquals("Rounded value is wrong", -1, MathWrapper.round(-0.654443453345), 0.000000001);
    }

    @Test
    public void absoluteValueWorks() {
        assertEquals("Absolute value is wrong", 5, MathWrapper.abs(-5), 0.000000001);
        assertEquals("Absolute value is wrong", 2, MathWrapper.abs(2), 0.000000001);
    }

    @Test
    public void randomNumberWorks() {
        double randomValue = MathWrapper.random();
        if (randomValue < 0.0 && 1.0 <= randomValue) {
            assertTrue("Random value is not >= 0 and < 1", false);
        } else {
            assert (true);
        }
    }

    @Test
    public void sqrtWorks() {
        assertEquals("Square root is wrong", 5, MathWrapper.sqrt(25), 0.000000001);
        assertEquals("Square root is wrong", 7, MathWrapper.sqrt(49), 0.000000001);
    }

    @Test
    public void powWorks() {
        assertEquals("Power of " + 5 + " is wrong", 32, MathWrapper.pow(2.0, 5.0), 0.000000001);
        assertEquals("Power of " + 8 + " is wrong", 256, MathWrapper.pow(2.0, 8.0), 0.000000001);
    }

    @Test
    public void minWorks() {
        assertEquals("Minimum of values is wrong", 5.0, MathWrapper.min(12.0, 5.0), 0.000000001);
        assertEquals("Minimum of values is wrong", -1234, MathWrapper.min(-1234, -123), 0.000000001);
    }

    @Test
    public void ceilWorks() {
        assertEquals("Ceil value is wrong", 6, MathWrapper.ceil(5.51883694824), 0.000000001);
        assertEquals("Ceil value is wrong", -1, MathWrapper.ceil(-1.854443453345), 0.000000001);
        assertEquals("Ceil value is wrong", 0, MathWrapper.ceil(-0.754443453345), 0.000000001);
    }
}
