/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flockSimulator.domain;

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
    private int WIDTH;
    private int HEIGHT;

    public AgentGenerator(double size, double awareness, double maxSpeed, double maxForce, int width, int height) {
        this.size = size;
        this.awareness = awareness;
        this.maxSpeed = maxSpeed;
        this.maxForce = maxForce;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.alignment = 1.0;
        this.separation = 1.5;
        this.cohesion = 1.0;
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
    
    public void createAgent(double x, double y) {
        Agent a = new Agent(x, y, this.size, this.awareness, this.maxSpeed, this.maxForce, this.WIDTH, this.HEIGHT);
    }

    
    // IMPLEMENT GENERATOR
}
