package flockSimulator.domain;

import java.util.ArrayList;
import javafx.scene.Node;

/**
 * Class for defining agents desired behaviors and parameters for those
 * behaviors
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
    private ArrayList<Agent> agents;
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
        this.agents = new ArrayList<>();
        this.rotationDelay = 1;
        this.delayCounter = 0;
    }

    /**
     * https://math.stackexchange.com/questions/377169/calculating-a-value-inside-one-range-to-a-value-of-another-range
     * MAP VALUE IN RANGE [a,b] TO OTHER RANGE [c,d]
     *
     */
    public double affineMap(double x, double a, double b, double c, double d) {
        if (b - a == 0) {
            return 0;
        } else {
            double y = (x - a) * ((d - c) / (b - a)) + c;
            return y;
        }
    }

    public Node createAgent(double x, double y) {
        Agent agent = new Agent(x, y, this.size, this.awareness, this.maxSpeed, this.maxForce, this.width, this.height);
        agents.add(agent);
        return agent.display();
    }

    // Update positions of all agents
    public void updateAgents(Vector mouse) {
        this.delayCounter = (this.delayCounter + 1) % this.rotationDelay;   // update rotation after rotationDelay amount of frames
        for (int i = 0; i < this.agents.size(); i++) {
            agentAction(this.agents.get(i), mouse);
        }
    }

    // Make agent behave in some way
    private void agentAction(Agent agent, Vector target) {
        agent.applyBehaviors(this.agents, target);
        agent.updatePosition();
        agent.checkEdges();
        if (this.delayCounter == 0) {
            agent.updateRotation();
        }
    }

    public ArrayList<Agent> getAgents() {
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
