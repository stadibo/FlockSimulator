package flocksimulator.domain;

import flocksimulator.util.Vector;
import javafx.scene.Node;

/**
 * Generator implementation using a brute force approach to querying agents
 *
 * @author peje
 */
public class AgentGenerator extends Generator {

    private final boolean rotation;
    

    public AgentGenerator(double size, double awareness, double maxSpeed, double maxForce, int width, int height, boolean rotation) {
        super(size, awareness, maxSpeed, maxForce, width, height);
        this.rotation = rotation;
    }
    
    /**
     * Method to initialize an agent based on parameters given to the generator
     *
     * @param x coordinate
     * @param y coordinate
     * @return Node object reference to the shape object stored in agent class
     */
    @Override
    public Node createAgent(double x, double y) {
        Agent agent = new Particle(x, y, this.size, this.awareness, this.maxSpeed, this.maxForce, this.width, this.height);
        agent.setAlignment(alignment);
        agent.setCohesion(cohesion);
        agent.setSeparation(separation);
        agents.add(agent);
        return agent.display();
    }

    /**
     * Update position of all agents
     *
     * @param target a point that can be used when combining behaviors, e.g.
     * seeking the mouse pointer
     */
    @Override
    public void updateAgents(Vector target) {
        for (int i = 0; i < this.agents.size(); i++) {
            agentAction(this.agents.get(i), target);
        }
    }

    /**
     * Make agent behave in some way and update their state
     *
     * @param agent to be updated
     * @param target to maybe be used in combining behaviors
     */
    @Override
    protected void agentAction(Agent agent, Vector target) {
        agent.applyBehaviors(this.agents, target);
        agent.updatePosition();
        if (this.rotation) {
            agent.updateRotation();
        }
    }

    /**
     * Empties the data structure of its elements
     */
    @Override
    public void clearAgents() {
        this.agents.clear();
    }
    
}
