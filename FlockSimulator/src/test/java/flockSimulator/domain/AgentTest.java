/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flockSimulator.domain;

import flockSimulator.domain.Vector;
import flockSimulator.domain.Agent;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Agent class
 * @author peje
 */
public class AgentTest {
    
    public AgentTest() {
    }
    
    @Test
    public void seekCreatesCorrectForce() {
        Agent a = new Agent(25, 25, 12, 100, 4, 0.2, 1280, 720);
        Vector target = new Vector(50, 50);
        Vector velo = new Vector(3,1);
        a.setVelocity(velo);
        
        Vector force = a.seek(target);
        a.applyForce(force);
        a.updatePosition();
        assertEquals("moves agent to wrong X position", 27.98131481932, a.getX(), 0.001);
        assertEquals("moves agent to wrong Y position", 26.19912524707, a.getY(), 0.0001);
    }
    
    @Test
    public void fleeCreatesCorrectForce() {
        Agent a = new Agent(25, 25, 12, 100, 4, 0.2, 1280, 720);
        Vector target = new Vector(50, 50);
        Vector velo = new Vector(2,1);
        a.setVelocity(velo);
        
        Vector force = a.flee(target);
        a.applyForce(force);
        a.updatePosition();
        assertEquals("moves agent to wrong X position", 26.84328442524, a.getX(), 0.001);
        assertEquals("moves agent to wrong Y position", 25.87574128349, a.getY(), 0.001);
    }
    
    @Test
    public void checkEdgesPosBoundary() {
        Agent a = new Agent(1281, 25, 12, 100, 4, 0.2, 1280, 720);
        Agent b = new Agent(12, 721, 12, 100, 4, 0.2, 1280, 720);
        
        a.checkEdges();
        b.checkEdges();
        
        assertEquals("moves agent to wrong X position", 0, a.getX(), 0.01);
        assertEquals("moves agent to wrong Y position", 25, a.getY(), 0.01);
        
        assertEquals("moves agent to wrong X position", 12, b.getX(), 0.01);
        assertEquals("moves agent to wrong Y position", 0, b.getY(), 0.01);
    }
    
    @Test
    public void checkEdgesNegBoundary() {
        Agent a = new Agent(-1, 25, 12, 100, 4, 0.2, 1280, 720);
        Agent b = new Agent(12, -1, 12, 100, 4, 0.2, 1280, 720);
        
        a.checkEdges();
        b.checkEdges();
        
        assertEquals("moves agent to wrong X position", 1280, a.getX(), 0.01);
        assertEquals("moves agent to wrong Y position", 25, a.getY(), 0.01);
        
        assertEquals("moves agent to wrong X position", 12, b.getX(), 0.01);
        assertEquals("moves agent to wrong Y position", 720, b.getY(), 0.01);
    }
    
    @Test
    public void separationCreatesCorrectForce() {
        ArrayList<Agent> list = new ArrayList<>();
        Agent a = new Agent(100, 100, 12, 100, 4, 0.2, 1280, 720);
        Agent b = new Agent(90, 100, 12, 100, 4, 0.2, 1280, 720);
        Agent c = new Agent(300, 300, 12, 100, 4, 0.2, 1280, 720);
        
        list.add(a);
        list.add(b);
        list.add(c);
        
        Vector v1 = a.separation(list);
        Vector v2 = b.separation(list);
        Vector v3 = c.separation(list);
        
        assertEquals("wrong X force", 0.2, v1.getX(), 0.01);
        assertEquals("wrong Y force", 0.0, v1.getY(), 0.01);
        
        assertEquals("wrong X force", -0.2, v2.getX(), 0.01);
        assertEquals("wrong Y force", 0.0, v2.getY(), 0.01);
        
        assertEquals("lone agent no separation force", 0.0, v3.getX(), 0.01);
        assertEquals("lone agent no separation force", 0.0, v3.getY(), 0.01);
    }
    
    @Test
    public void alignmentCreatesCorrectForce() {
        ArrayList<Agent> list = new ArrayList<>();
        Agent a = new Agent(100, 100, 12, 100, 4, 0.2, 1280, 720);
        a.setVelocity(new Vector(1.0, 0.0));
        
        Agent b = new Agent(80, 100, 12, 100, 4, 0.2, 1280, 720);
        b.setVelocity(new Vector(0.0, 1.0));
        
        Agent c = new Agent(300, 300, 12, 100, 4, 0.2, 1280, 720);
        c.setVelocity(new Vector(3.0, 1.0));
        
        list.add(a);
        list.add(b);
        list.add(c);
        
        Vector v1 = a.alignment(list);
        Vector v2 = b.alignment(list);
        Vector v3 = c.alignment(list);
        
        assertEquals("wrong X force", -0.048507, v1.getX(), 0.01);
        assertEquals("wrong Y force", 0.1940285, v1.getY(), 0.01);
        
        assertEquals("wrong X force", 0.1940285, v2.getX(), 0.01);
        assertEquals("wrong Y force", -0.048507, v2.getY(), 0.01);
        
        assertEquals("lone agent no separation force", 0.0, v3.getX(), 0.01);
        assertEquals("lone agent no separation force", 0.0, v3.getY(), 0.01);
    }
    
    @Test
    public void cohesionCreatesCorrectForce() {
        ArrayList<Agent> list = new ArrayList<>();
        Agent a = new Agent(120, 120, 12, 100, 4, 0.3, 1280, 720);
        Agent b = new Agent(80, 80, 12, 100, 4, 0.3, 1280, 720);
        Agent c = new Agent(120, 80, 12, 100, 4, 0.3, 1280, 720);
        Agent d = new Agent(80, 120, 12, 100, 4, 0.3, 1280, 720);
        Agent e = new Agent(300, 300, 12, 100, 4, 0.3, 1280, 720);
        
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);
        
        Vector v1 = a.cohesion(list);
        Vector v2 = b.cohesion(list);
        Vector v3 = c.cohesion(list);
        Vector v4 = d.cohesion(list);
        Vector v5 = e.cohesion(list);
        
        assertEquals("wrong X force", -0.212132, v1.getX(), 0.01);
        assertEquals("wrong Y force", -0.212132, v1.getY(), 0.01);
        
        assertEquals("wrong X force", 0.212132, v2.getX(), 0.01);
        assertEquals("wrong Y force", 0.212132, v2.getY(), 0.01);
        
        assertEquals("wrong X force", -0.212132, v3.getX(), 0.01);
        assertEquals("wrong Y force", 0.212132, v3.getY(), 0.01);
        
        assertEquals("wrong X force", 0.212132, v4.getX(), 0.01);
        assertEquals("wrong Y force", -0.212132, v4.getY(), 0.01);
        
        assertEquals("lone agent no separation force", 0.0, v5.getX(), 0.01);
        assertEquals("lone agent no separation force", 0.0, v5.getY(), 0.01);
    }
    
    @Test
    public void updatePositionSetsValuesCorrectly() {
        Agent a = new Agent(120, 120, 12, 100, 4, 0.3, 1280, 720);
        a.setVelocity(new Vector(1.0, 0.0));
        a.applyForce(new Vector(0.2, 0.0));
        a.updatePosition();
        
        assertEquals("wrong X force", 121.2, a.getX(), 0.01);
        assertEquals("wrong Y force", 120, a.getY(), 0.01);
    }
    
    
    
}
