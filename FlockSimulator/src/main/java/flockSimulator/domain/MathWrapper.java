/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flockSimulator.domain;

import java.lang.Math;

/**
 * Class for math operations. java.lang.Math class wrapped for flockSimulator
 * project
 *
 * @author peje
 */
public class MathWrapper {

    private MathWrapper() {
    }

    public static final double sin(double value) {
        return Math.sin(value);
    }

    public static final double cos(double value) {
        return Math.cos(value);
    }

    public static final double atan2(double v1, double v2) {
        return Math.atan2(v1, v2);
    }

    public static final long round(double value) {
        return Math.round(value);
    }

    public static final double abs(double value) {
        return Math.abs(value);
    }

    public static final double random() {
        return Math.random();
    }

    public static final double sqrt(double value) {
        return Math.sqrt(value);
    }

    public static final double pow(double value, double exponent) {
        return Math.pow(value, exponent);
    }

    public static final double min(double v1, double v2) {
        return Math.min(v1, v2);
    }

    public static final double ceil(double value) {
        return Math.ceil(value);
    }
}
