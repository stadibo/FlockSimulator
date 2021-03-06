package flocksimulator.domain;

import flocksimulator.util.Vector;
import flocksimulator.util.BinLattice;
import javafx.scene.Node;

/**
 * Generator implementation using the Bin-lattice data structure for faster
 * querying of agents
 *
 * @author peje
 */
public class SpatialAgentGenerator extends Generator {

    private BinLattice<Agent> agentGrid;
    private final boolean rotation;

    public SpatialAgentGenerator(double size, double awareness, double maxSpeed, double maxForce, int width, int height, boolean rotation, int cellSize) {
        super(size, awareness, maxSpeed, maxForce, width, height);
        this.rotation = rotation;
        this.agentGrid = new BinLattice(width, height, cellSize);
        this.agentGrid.initGrid();
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
     * Update positions of all agents
     *
     * @param target a point that can be used when combining behaviors, e.g.
     * seeking the mouse pointer
     */
    @Override
    public void updateAgents(Vector target) {
        this.agentGrid.clearGrid();
        this.fillGrid();
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
        int x = (int) agent.getX();
        int y = (int) agent.getY();
        agent.applyBehaviors(this.agentGrid.getNearestNeighbors(x, y), target);
        agent.updatePosition();
        if (this.rotation) {
            agent.updateRotation();
        }
    }

    /**
     * Inserts all agents to grid based on their position
     */
    private void fillGrid() {
        for (int i = 0; i < this.agents.size(); i++) {
            int x = (int) this.agents.get(i).getX();
            int y = (int) this.agents.get(i).getY();
            this.agentGrid.insert(x, y, this.agents.get(i));
        }
    }

    /**
     * Empties the data structures of their elements
     */
    @Override
    public void clearAgents() {
        this.agents.clear();
        this.agentGrid.clearGrid();
    }

}
