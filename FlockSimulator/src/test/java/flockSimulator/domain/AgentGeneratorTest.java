/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flockSimulator.domain;

import javafx.scene.Node;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for AgentGenerator class
 * @author peje
 */
public class AgentGeneratorTest {
    
    private AgentGenerator g1;
    
    public AgentGeneratorTest() {   
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.g1 = new AgentGenerator(
                12.0,   // radius
                100.0,  // awareness
                4,      // maxSpeed
                0.2,    // maxForce
                1280,   // width
                720);   // height
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void createAgentReturnsObject() {
        Node returnedObject = g1.createAgent(0, 0);
        assertNotNull(returnedObject);
        
        assertTrue(returnedObject instanceof Node);
    }
    
    @Test
    public void createAgentMakesCorrectAgent() {
        g1.createAgent(40, 40);
        Agent a = g1.getAgents().get(0);
        assertEquals("", 40, a.getX(), 0.01);
        assertEquals("", 40, a.getY(), 0.01);
        assertEquals("", 0, a.getVelocity().getX(), 0.01);
        assertEquals("", 0, a.getVelocity().getY(), 0.01);
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
        
        g1.updateAgents(new Vector(0,0));
        
        a = g1.getAgents().get(0);
        b = g1.getAgents().get(1);
        c = g1.getAgents().get(2);
        
        assertEquals("moves agent to wrong X position", 101.05149, a.getX(), 0.0001);
        assertEquals("moves agent to wrong Y position", 100.194028, a.getY(), 0.0001);
        
        assertEquals("moves agent to wrong X position", 80.103557, b.getX(), 0.0001);
        assertEquals("moves agent to wrong Y position", 100.863994, b.getY(), 0.01);
        
        assertEquals("moves agent to wrong X position", 303.0, c.getX(), 0.01);
        assertEquals("moves agent to wrong Y position", 301.0, c.getY(), 0.01);
    }
    
}
