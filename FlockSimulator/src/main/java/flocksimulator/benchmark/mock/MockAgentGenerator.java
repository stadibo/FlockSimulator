package flocksimulator.benchmark.mock;

import flocksimulator.domain.Agent;
import flocksimulator.domain.AgentGenerator;
import javafx.scene.Node;

/**
 * Benchmark version of AgentGenerator class to use MockAgents
 * @author peje
 */
public class MockAgentGenerator extends AgentGenerator {

    public MockAgentGenerator(double size, double awareness, double maxSpeed, double maxForce, int width, int height, boolean rotation) {
        super(size, awareness, maxSpeed, maxForce, width, height, rotation);
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
        Agent agent = new MockAgent(x, y, this.size, this.awareness, this.maxSpeed, this.maxForce, this.width, this.height);
        agent.setAlignment(alignment);
        agent.setCohesion(cohesion);
        agent.setSeparation(separation);
        agents.add(agent);
        return agent.display();
    }

}
