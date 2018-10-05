package flockSimulator.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peje
 */
public class SpatialAgentGeneratorTest {

    private Generator g1;

    public SpatialAgentGeneratorTest() {
    }

    @Before
    public void setUp() {
        this.g1 = new SpatialAgentGenerator(
                12.0, // radius
                100.0, // awareness
                4, // maxSpeed
                0.2, // maxForce
                1280, // width
                720, // height
                false, // rotation
                40 // cell size
        );
    }

    @Test
    public void updateAgentsWroksAsIntended() {
        Agent a = new Agent(100, 100, 12, 100, 4, 0.2, 1280, 720);
        a.setVelocity(new Vector(1.0, 0.0));

        Agent b = new Agent(80, 100, 12, 100, 4, 0.2, 1280, 720);
        b.setVelocity(new Vector(0.0, 1.0));

        Agent c = new Agent(300, 300, 12, 100, 4, 0.2, 1280, 720);
        c.setVelocity(new Vector(3.0, 1.0));

        g1.getAgents().add(a);
        g1.getAgents().add(b);
        g1.getAgents().add(c);

        g1.setAlignment(1.0);
        g1.setCohesion(1.0);
        g1.setSeparation(1.5);

        g1.updateAgents(new Vector(0, 0));

        a = g1.getAgents().get(0);
        b = g1.getAgents().get(1);
        c = g1.getAgents().get(2);

        assertEquals("moves agent to wrong X position", 101.05149, a.getX(), 0.01);
        assertEquals("moves agent to wrong Y position", 100.194028, a.getY(), 0.01);

        assertEquals("moves agent to wrong X position", 80.103557, b.getX(), 0.01);
        assertEquals("moves agent to wrong Y position", 100.863994, b.getY(), 0.01);

        assertEquals("moves agent to wrong X position", 303.0, c.getX(), 0.01);
        assertEquals("moves agent to wrong Y position", 301.0, c.getY(), 0.01);
    }

    @Test
    public void clearingStructuresWorks() {
        Agent a = new Agent(100, 100, 12, 100, 4, 0.2, 1280, 720);
        Agent b = new Agent(80, 100, 12, 100, 4, 0.2, 1280, 720);
        Agent c = new Agent(300, 300, 12, 100, 4, 0.2, 1280, 720);

        g1.getAgents().add(a);
        g1.getAgents().add(b);
        g1.getAgents().add(c);
        
        g1.setAlignment(1.0);
        g1.setCohesion(1.0);
        g1.setSeparation(1.5);
        
        g1.updateAgents(new Vector(0, 0));
        
        g1.clearAgents();
        assertTrue(g1.getAgentsSize() == 0);
    }

}
