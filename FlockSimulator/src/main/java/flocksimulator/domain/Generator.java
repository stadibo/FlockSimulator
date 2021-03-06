package flocksimulator.domain;

import flocksimulator.util.Vector;
import flocksimulator.util.FlockList;
import javafx.scene.Node;

/**
 * Class for defining agents desired behaviors and parameters for those
 * behaviors, as well as, creating agents and keeping track of them. The way of
 * querying agents is specified by the implementations of this class
 *
 * @author peje
 */
public abstract class Generator {

    protected double size;   // Size of agent / collision radius
    protected double awareness; // How far the agent can see
    protected double maxSpeed;    // Maximum speed
    protected double maxForce;    // Maximum steering force
    protected double alignment;   // Modifier
    protected double separation;  // Modifier
    protected double cohesion;    // Modifier
    protected int width;
    protected int height;
    protected FlockList<Agent> agents;

    public Generator(double size, double awareness, double maxSpeed, double maxForce, int width, int height) {
        this.size = size;
        this.awareness = awareness;
        this.maxSpeed = maxSpeed;
        this.maxForce = maxForce;
        this.width = width;
        this.height = height;
        this.agents = new FlockList<>(100);
    }

    /**
     * Method to initialize an agent based on parameters given to the generator
     *
     * @param x coordinate
     * @param y coordinate
     * @return Node object reference to the shape object stored in agent class
     */
    public abstract Node createAgent(double x, double y);

    /**
     * Update positions of all agents
     *
     * @param target a point that can be used when combining behaviors, e.g.
     * seeking the mouse pointer
     */
    public abstract void updateAgents(Vector target);

    /**
     * Make agent behave in some way and update their state
     *
     * @param agent to be updated
     * @param target to maybe be used in combining behaviors
     */
    protected abstract void agentAction(Agent agent, Vector target);

    /**
     * Empties the data structures of their elements
     */
    public abstract void clearAgents();

    public FlockList<Agent> getAgents() {
        return agents;
    }

    public int getAgentsSize() {
        return agents.size();
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;

        for (int i = 0; i < this.agents.size(); i++) {
            this.agents.get(i).setMaxSpeed(maxSpeed);
        }
    }

    public void setMaxForce(double maxForce) {
        this.maxForce = maxForce;

        for (int i = 0; i < this.agents.size(); i++) {
            this.agents.get(i).setMaxForce(maxForce);
        }
    }
    
    public void setAlignment(double alignment) {
        for (int i = 0; i < this.agents.size(); i++) {
            this.agents.get(i).setAlignment(alignment);
        }
        this.alignment = alignment;
    }

    public void setSeparation(double separation) {
        for (int i = 0; i < this.agents.size(); i++) {
            this.agents.get(i).setSeparation(separation);
        }
        this.separation = separation;
    }

    public void setCohesion(double cohesion) {
        for (int i = 0; i < this.agents.size(); i++) {
            this.agents.get(i).setCohesion(cohesion);
        }
        this.cohesion = cohesion;
    }

}
