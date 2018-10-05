package flockSimulator.domain;

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
     * Update positions of all agents
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
