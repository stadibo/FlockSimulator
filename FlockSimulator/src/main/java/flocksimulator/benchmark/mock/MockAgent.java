package flocksimulator.benchmark.mock;

import flocksimulator.domain.Agent;
import flocksimulator.util.FlockList;
import flocksimulator.util.Vector;
import javafx.scene.shape.Ellipse;

/**
 * Benchmark version of Agent class to use slower distance calculation
 *
 * @author peje
 */
public class MockAgent extends Agent {

    private double separation;
    private double alignment;
    private double cohesion;
    
    
    public MockAgent(double x, double y, double size, double awareness, double maxSpeed, double maxForce, int w, int h) {
        super(x, y, size, awareness, maxSpeed, maxForce, w, h);
        this.poly = new Ellipse(6.0, 6.0);
    }

    /**
     * Method for creating a force by which to repel from other agents based on
     * their distance if they are within the view radius of this agent.
     *
     * @param agents List of agents to check for proximity
     * @return force vector to be applied to agents velocity
     */
    public Vector separation(FlockList<Agent> agents) {
        double desiredSeparation = this.size * 2;
        Vector sum = new Vector();
        int counter = 0;
        for (int i = 0; i < agents.size(); i++) {
            double dist = this.position.distance(agents.get(i).getPosition());
            if ((dist > 0) && (dist < desiredSeparation)) {
                Vector offset = new Vector().sub(this.position, agents.get(i).getPosition());
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
     * of this agent, and creating a steering force to correct this agents
     * heading based on that average.
     *
     * @param agents List of agents to check for proximity
     * @return force vector to be applied to agents velocity
     */
    public Vector alignment(FlockList<Agent> agents) {
        Vector sum = new Vector();  // sum of velocities
        int counter = 0;
        for (int i = 0; i < agents.size(); i++) {
            double dist = this.position.distance(agents.get(i).getPosition());
            if ((dist > 0) && (dist < this.awareness)) {
                sum.add(agents.get(i).getVelocity());
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
     * other agents if they are within the view radius of this agent.
     *
     * @param agents List of agents to check for proximity
     * @return force vector to be applied to agents velocity
     */
    public Vector cohesion(FlockList<Agent> agents) {
        Vector sum = new Vector();  // sum of positions
        int counter = 0;
        for (int i = 0; i < agents.size(); i++) {
            double dist = this.position.distance(agents.get(i).getPosition());
            if ((dist > 0) && (dist < this.awareness)) {
                sum.add(agents.get(i).getPosition());
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
     * Combine behaviors for flocking, and scale them by externally set
     * modifiers.
     *
     * @param agents list of other agents which are neighbors to this agent
     */
    public void flock(FlockList<Agent> agents) {
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

    @Override
    public void setAlignment(double alignment) {
        this.alignment = alignment;
    }

    @Override
    public void setSeparation(double separation) {
        this.separation = separation;
    }

    @Override
    public void setCohesion(double cohesion) {
        this.cohesion = cohesion;
    }

    @Override
    public void applyBehaviors(FlockList<Agent> agents, Vector target) {
        this.flock(agents);
    }
}
