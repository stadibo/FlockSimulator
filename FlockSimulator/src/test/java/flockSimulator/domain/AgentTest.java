/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flockSimulator.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peje
 */
public class AgentTest {
    
    public AgentTest() {
    }
    
    @Test
    public void seekCreatesCorrectForce() {
        Agent a = new Agent(25, 25);
        Vector target = new Vector(50, 50);
        Vector velo = new Vector(3,1);
        a.setVelocity(velo);
        
        a.seek(target);
        a.updatePosition();
        assertEquals("moves agent to wrong X position", 27.99065, a.getX(), 0.01);
        assertEquals("moves agent to wrong Y position", 26.09956, a.getY(), 0.01);
    }
    
    @Test
    public void fleeCreatesCorrectForce() {
        Agent a = new Agent(25, 25);
        Vector target = new Vector(50, 50);
        Vector velo = new Vector(2,1);
        a.setVelocity(velo);
        
        a.flee(target);
        a.updatePosition();
        assertEquals("moves agent to wrong X position", 26.92164, a.getX(), 0.01);
        assertEquals("moves agent to wrong Y position", 25.93787, a.getY(), 0.01);
    }
    
    @Test
    public void checkEdgesPosBoundary() {
        Agent a = new Agent(1281, 25);
        Agent b = new Agent(12, 721);
        
        a.checkEdges();
        b.checkEdges();
        
        assertEquals("moves agent to wrong X position", 0, a.getX(), 0.01);
        assertEquals("moves agent to wrong Y position", 25, a.getY(), 0.01);
        
        assertEquals("moves agent to wrong X position", 12, b.getX(), 0.01);
        assertEquals("moves agent to wrong Y position", 0, b.getY(), 0.01);
    }
    
    @Test
    public void checkEdgesNegBoundary() {
        Agent a = new Agent(-1, 25);
        Agent b = new Agent(12, -1);
        
        a.checkEdges();
        b.checkEdges();
        
        assertEquals("moves agent to wrong X position", 1280, a.getX(), 0.01);
        assertEquals("moves agent to wrong Y position", 25, a.getY(), 0.01);
        
        assertEquals("moves agent to wrong X position", 12, b.getX(), 0.01);
        assertEquals("moves agent to wrong Y position", 720, b.getY(), 0.01);
    }
    
}
