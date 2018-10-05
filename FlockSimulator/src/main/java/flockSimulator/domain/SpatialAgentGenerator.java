package flockSimulator.domain;

import flockSimulator.util.BinLattice;

/**
 * Generator implementation using the Bin-lattice data structure for faster
 * querying of agents
 *
 * @author peje
 */
public class SpatialAgentGenerator extends Generator {

    private BinLattice agentGrid;
    private final boolean rotation;

    public SpatialAgentGenerator(double size, double awareness, double maxSpeed, double maxForce, int width, int height, boolean rotation) {
        super(size, awareness, maxSpeed, maxForce, width, height);
        this.rotation = rotation;
        this.agentGrid = new BinLattice(width, height, 40);
        this.agentGrid.initGrid();
    }

    @Override
    public void updateAgents(Vector target) {
        this.agentGrid.clearGrid();
        this.fillGrid();
        for (int i = 0; i < this.agents.size(); i++) {
            agentAction(this.agents.get(i), target);
        }
    }

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

    private void fillGrid() {
        for (int i = 0; i < this.agents.size(); i++) {
            int x = (int) this.agents.get(i).getX();
            int y = (int) this.agents.get(i).getY();
            this.agentGrid.insert(x, y, this.agents.get(i));
        }
    }

    @Override
    public void clearAgents() {
        this.agents.clear();
        this.agentGrid.clearGrid();
    }

}
