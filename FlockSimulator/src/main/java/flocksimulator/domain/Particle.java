package flocksimulator.domain;

import javafx.scene.shape.Ellipse;

/**
 * Implementation of an agent with flocking behavior, but the appearance of a
 * "particle/molecule"
 *
 * @author peje
 */
public class Particle extends Flocker {

    public Particle(double x, double y, double size, double awareness, double maxSpeed, double maxForce, int w, int h) {
        super(x, y, size, awareness, maxSpeed, maxForce, w, h);
        this.poly = new Ellipse(6.0, 6.0);
    }

}
