package flockSimulator.domain;

import flockSimulator.util.FlockList;
import java.util.ArrayList;
import javafx.scene.Node;

/**
 * Class for defining agents desired behaviors and parameters for those
 * behaviors, as well as, creating agents and keeping track of them.
 *
 * @author peje
 */
public class AgentGenerator {

    private double size;   // Size of agent / collision radius
    private double awareness; // How far the agent can see
    private double maxSpeed;    // Maximum speed
    private double maxForce;    // Maximum steering force
    private double alignment;
    private double separation;
    private double cohesion;
    private int width;
    private int height;
    private FlockList<Agent> agents;
    private int rotationDelay;
    private int delayCounter;

    public AgentGenerator(double size, double awareness, double maxSpeed, double maxForce, int width, int height) {
        this.size = size;
        this.awareness = awareness;
        this.maxSpeed = maxSpeed;
        this.maxForce = maxForce;
        this.width = width;
        this.height = height;
        this.alignment = 1.0;
        this.separation = 1.5;
        this.cohesion = 1.0;
        this.agents = new FlockList<>(100);
        this.rotationDelay = 1;
        this.delayCounter = 0;
    }

    /**
     * Method to initialize an agent based on parameters given to the generator
     *
     * @param x coordinate
     * @param y coordinate
     * @return Node object reference to the shape object stored in agent class
     */
    public Node createAgent(double x, double y) {
        Agent agent = new Agent(x, y, this.size, this.awareness, this.maxSpeed, this.maxForce, this.width, this.height);
        agents.add(agent);
        return agent.display();
    }

    /**
     * Update positions of all agents
     *
     * @param target a point that can be used when combining behaviors, e.g.
     * seeking the mouse pointer
     */
    public void updateAgents(Vector target) {
        for (int i = 0; i < this.agents.size(); i++) {
            agentAction(this.agents.get(i), target);
        }
        this.delayCounter = (this.delayCounter + 1) % this.rotationDelay;   // update rotation after rotationDelay amount of frames
    }

    /**
     * Make agent behave in some way and update their state
     *
     * @param agent to be updated
     * @param target to maybe be used in combining behaviors
     */
    private void agentAction(Agent agent, Vector target) {
        agent.applyBehaviors(this.agents, target);
        agent.updatePosition();
        agent.checkEdges();
        if (this.delayCounter == 0) {
            agent.updateRotation();
        }
    }

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
        this.alignment = alignment;

        for (int i = 0; i < this.agents.size(); i++) {
            this.agents.get(i).setAlignment(alignment);
        }
    }

    public void setSeparation(double separation) {
        this.separation = separation;

        for (int i = 0; i < this.agents.size(); i++) {
            this.agents.get(i).setSeparation(separation);
        }
    }

    public void setCohesion(double cohesion) {
        this.cohesion = cohesion;

        for (int i = 0; i < this.agents.size(); i++) {
            this.agents.get(i).setCohesion(cohesion);
        }
    }
}
