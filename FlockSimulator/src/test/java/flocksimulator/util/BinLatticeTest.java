package flocksimulator.util;

import flocksimulator.util.BinLattice;
import flocksimulator.domain.Agent;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Bin-Lattice data structure
 * @author peje
 */
public class BinLatticeTest {
    
    private BinLattice grid;
    
    public BinLatticeTest() {
    }
    
    @Before
    public void setUp() {
        this.grid = new BinLattice(1280, 720, 10);
    }
    
    @Test
    public void initializationWorks() {
        this.grid.initGrid();
    }
    
    @Test
    public void insertingWorks() {
        this.grid.initGrid();
        Agent a = new Agent(40, 40, 12, 100, 4, 0.2, 1280, 720);
        Agent b = new Agent(41, 41, 12, 100, 4, 0.2, 1280, 720);
        this.grid.insert(40, 40, a);
        this.grid.insert(41, 41, b);
        assertTrue(this.grid.getNearestNeighbors(40, 40).get(1).equals(b));
    }
    
    @Test
    public void clearingGridWorks() {
        this.grid.initGrid();
        Agent a = new Agent(40, 40, 12, 100, 4, 0.2, 1280, 720);
        Agent b = new Agent(80, 80, 12, 100, 4, 0.2, 1280, 720);
        this.grid.insert(40, 40, a);
        this.grid.insert(80, 80, b);
        this.grid.clearGrid();
        assertTrue(this.grid.getNearestNeighbors(40, 40).size() == 0);
        assertTrue(this.grid.getNearestNeighbors(80, 80).size() == 0);
    }
    
    @Test
    public void gettingNeighborsWorks() {
        this.grid.initGrid();
        Agent a = new Agent(40, 40, 12, 100, 4, 0.2, 1280, 720);
        Agent b = new Agent(45, 45, 12, 100, 4, 0.2, 1280, 720);
        Agent c = new Agent(100, 100, 12, 100, 4, 0.2, 1280, 720);
        this.grid.insert(40, 40, a);
        this.grid.insert(45, 45, b);
        this.grid.insert(100, 100, c);
        assertTrue(this.grid.getNearestNeighbors(40, 40).get(1).equals(b));
        assertTrue(this.grid.getNearestNeighbors(105, 105).get(0).equals(c));
    }
}
