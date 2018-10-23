package flocksimulator.domain;

import flocksimulator.util.Vector;
import flocksimulator.util.FlockList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Flocker class
 * @author peje
 */
public class FlockerTest {
    
    public FlockerTest() {
    }
    
    @Test
    public void seekCreatesCorrectForce() {
        Flocker a = new Flocker(25, 25, 12, 100, 4, 0.2, 1280, 720);
        Vector target = new Vector(50, 50);
        Vector velo = new Vector(3,1);
        a.setVelocity(velo);
        
        Vector force = a.seek(target);
        a.applyForce(force);
        a.updatePosition();
        assertEquals("moves Flocker to wrong X position", 27.98131481932, a.getX(), 0.001);
        assertEquals("moves Flocker to wrong Y position", 26.19912524707, a.getY(), 0.0001);
    }
    
    @Test
    public void fleeCreatesCorrectForce() {
        Flocker a = new Flocker(25, 25, 12, 100, 4, 0.2, 1280, 720);
        Vector target = new Vector(50, 50);
        Vector velo = new Vector(2,1);
        a.setVelocity(velo);
        
        Vector force = a.flee(target);
        a.applyForce(force);
        a.updatePosition();
        assertEquals("moves Flocker to wrong X position", 26.84328442524, a.getX(), 0.001);
        assertEquals("moves Flocker to wrong Y position", 25.87574128349, a.getY(), 0.001);
    }
    
    @Test
    public void checkEdgesPosBoundary() {
        Flocker a = new Flocker(1281, 25, 12, 100, 4, 0.2, 1280, 720);
        Flocker b = new Flocker(12, 721, 12, 100, 4, 0.2, 1280, 720);
        
        a.updatePosition();
        b.updatePosition();
        
        assertEquals("moves Flocker to wrong X position", 0, a.getX(), 0.01);
        assertEquals("moves Flocker to wrong Y position", 25, a.getY(), 0.01);
        
        assertEquals("moves Flocker to wrong X position", 12, b.getX(), 0.01);
        assertEquals("moves Flocker to wrong Y position", 0, b.getY(), 0.01);
    }
    
    @Test
    public void checkEdgesNegBoundary() {
        Flocker a = new Flocker(-1, 25, 12, 100, 4, 0.2, 1280, 720);
        Flocker b = new Flocker(12, -1, 12, 100, 4, 0.2, 1280, 720);
        
        a.updatePosition();
        b.updatePosition();
        
        assertEquals("moves Flocker to wrong X position", 1280, a.getX(), 0.01);
        assertEquals("moves Flocker to wrong Y position", 25, a.getY(), 0.01);
        
        assertEquals("moves Flocker to wrong X position", 12, b.getX(), 0.01);
        assertEquals("moves Flocker to wrong Y position", 720, b.getY(), 0.01);
    }
    
    @Test
    public void separationCreatesCorrectForce() {
        FlockList<Agent> list = new FlockList<>();
        Flocker a = new Flocker(100, 100, 12, 100, 4, 0.2, 1280, 720);
        Flocker b = new Flocker(90, 100, 12, 100, 4, 0.2, 1280, 720);
        Flocker c = new Flocker(300, 300, 12, 100, 4, 0.2, 1280, 720);
        
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
        
        assertEquals("lone Flocker no separation force", 0.0, v3.getX(), 0.01);
        assertEquals("lone Flocker no separation force", 0.0, v3.getY(), 0.01);
    }
    
    @Test
    public void alignmentCreatesCorrectForce() {
        FlockList<Agent> list = new FlockList<>();
        Flocker a = new Flocker(100, 100, 12, 100, 4, 0.2, 1280, 720);
        a.setVelocity(new Vector(1.0, 0.0));
        
        Flocker b = new Flocker(80, 100, 12, 100, 4, 0.2, 1280, 720);
        b.setVelocity(new Vector(0.0, 1.0));
        
        Flocker c = new Flocker(300, 300, 12, 100, 4, 0.2, 1280, 720);
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
        
        assertEquals("lone Flocker no separation force", 0.0, v3.getX(), 0.01);
        assertEquals("lone Flocker no separation force", 0.0, v3.getY(), 0.01);
    }
    
    @Test
    public void cohesionCreatesCorrectForce() {
        FlockList<Agent> list = new FlockList<>();
        Flocker a = new Flocker(120, 120, 12, 100, 4, 0.3, 1280, 720);
        Flocker b = new Flocker(80, 80, 12, 100, 4, 0.3, 1280, 720);
        Flocker c = new Flocker(120, 80, 12, 100, 4, 0.3, 1280, 720);
        Flocker d = new Flocker(80, 120, 12, 100, 4, 0.3, 1280, 720);
        Flocker e = new Flocker(300, 300, 12, 100, 4, 0.3, 1280, 720);
        
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
        
        assertEquals("lone Flocker no separation force", 0.0, v5.getX(), 0.01);
        assertEquals("lone Flocker no separation force", 0.0, v5.getY(), 0.01);
    }
    
    @Test
    public void updatePositionSetsValuesCorrectly() {
        Agent a = new Flocker(120, 120, 12, 100, 4, 0.3, 1280, 720);
        a.setVelocity(new Vector(1.0, 0.0));
        a.applyForce(new Vector(0.2, 0.0));
        a.updatePosition();
        
        assertEquals("wrong X force", 121.2, a.getX(), 0.01);
        assertEquals("wrong Y force", 120, a.getY(), 0.01);
    }
    
    @Test
    public void rotationIsSetCorrectly() {
        Agent a = new Flocker(120, 120, 12, 100, 4, 0.3, 1280, 720);
        a.setVelocity(new Vector(2.0, 0.0));
        a.updateRotation();
        assertEquals("wrong angle", 0.0, a.display().getRotate(), 0.01);
        
        a.setVelocity(new Vector(5.0, 3.0));
        a.updateRotation();
        assertEquals("wrong angle", 30.96375, a.display().getRotate(), 0.01);
    }
    
}
