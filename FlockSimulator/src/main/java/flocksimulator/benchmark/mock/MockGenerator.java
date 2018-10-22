package flocksimulator.benchmark.mock;

import flocksimulator.domain.Vector;
import java.util.ArrayList;
import javafx.scene.Node;

/**
 * Benchmark version of Generator class to test java ArrayList as data structure
 * @author peje
 */
public abstract class MockGenerator {
    
    protected ArrayList<MockAgent> agents;

    protected double size;   // Size of agent / collision radius
    protected double awareness; // How far the agent can see
    protected double maxSpeed;    // Maximum speed
    protected double maxForce;    // Maximum steering force
    protected int width;
    protected int height;

    public MockGenerator(double size, double awareness, double maxSpeed, double maxForce, int width, int height) {
        this.size = size;
        this.awareness = awareness;
        this.maxSpeed = maxSpeed;
        this.maxForce = maxForce;
        this.width = width;
        this.height = height;
        this.agents = new ArrayList<>(100);
    }

    /**
     * Method to initialize an agent based on parameters given to the generator
     *
     * @param x coordinate
     * @param y coordinate
     * @return Node object reference to the shape object stored in agent class
     */
    public Node createAgent(double x, double y) {
        MockAgent agent = new MockAgent(x, y, this.size, this.awareness, this.maxSpeed, this.maxForce, this.width, this.height);
        agents.add(agent);
        return agent.display();
    }

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
    protected abstract void agentAction(MockAgent agent, Vector target);

    /**
     * Empties the data structures of their elements
     */
    public abstract void clearAgents();

    public ArrayList<MockAgent> getAgents() {
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
    }

    public void setSeparation(double separation) {
        for (int i = 0; i < this.agents.size(); i++) {
            this.agents.get(i).setSeparation(separation);
        }
    }

    public void setCohesion(double cohesion) {
        for (int i = 0; i < this.agents.size(); i++) {
            this.agents.get(i).setCohesion(cohesion);
        }
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
