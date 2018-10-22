package flocksimulator.util;

import flocksimulator.domain.Agent;
import flocksimulator.domain.Flocker;
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
        this.grid = new BinLattice(1280, 720, 20);
    }
    
    @Test
    public void cannotCreateGridWithNegativeHeight() {
        try {
            this.grid = new BinLattice(1280, -720, 20);
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }
    
    @Test
    public void cannotCreateGridWithNegativeWidth() {
        try {
            this.grid = new BinLattice(-1280, 720, 20);
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }
    
    @Test
    public void cannotCreateGridWithNegativeScale() {
        try {
            this.grid = new BinLattice(1280, 720, -20);
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }
    
    @Test
    public void initializationWorks() {
        this.grid.initGrid();
    }
    
    
    @Test
    public void insertingWorks() {
        this.grid.initGrid();
        Agent a = new Flocker(40, 40, 12, 100, 4, 0.2, 1280, 720);
        Agent b = new Flocker(41, 41, 12, 100, 4, 0.2, 1280, 720);
        this.grid.insert(40, 40, a);
        this.grid.insert(41, 41, b);
        assertTrue(this.grid.getNearestNeighbors(40, 40).get(1).equals(b));
    }
    
    @Test
    public void clearingGridWorks() {
        this.grid.initGrid();
        Agent a = new Flocker(40, 40, 12, 100, 4, 0.2, 1280, 720);
        Agent b = new Flocker(80, 80, 12, 100, 4, 0.2, 1280, 720);
        this.grid.insert(40, 40, a);
        this.grid.insert(80, 80, b);
        this.grid.clearGrid();
        assertTrue(this.grid.getNearestNeighbors(40, 40).size() == 0);
        assertTrue(this.grid.getNearestNeighbors(80, 80).size() == 0);
    }
    
    @Test
    public void gettingNeighborsWorks() {
        this.grid.initGrid();
        Agent a = new Flocker(50, 50, 12, 100, 4, 0.2, 1280, 720);
        Agent b = new Flocker(35, 35, 12, 100, 4, 0.2, 1280, 720);
        Agent c = new Flocker(35, 50, 12, 100, 4, 0.2, 1280, 720);
        Agent d = new Flocker(35, 60, 12, 100, 4, 0.2, 1280, 720);
        Agent e = new Flocker(85, 15, 12, 100, 4, 0.2, 1280, 720);
        Agent f = new Flocker(85, 85, 12, 100, 4, 0.2, 1280, 720);
        Agent g = new Flocker(15, 85, 12, 100, 4, 0.2, 1280, 720);
        Agent h = new Flocker(15, 15, 12, 100, 4, 0.2, 1280, 720);
        
        this.grid.insert(50, 50, a);
        this.grid.insert(35, 35, b);
        this.grid.insert(35, 50, c);
        this.grid.insert(35, 65, d);
        this.grid.insert(1280, 720, e);
        this.grid.insert(1280, 0, f);
        this.grid.insert(1280, 720, g);
        this.grid.insert(0, 720, h);
        
        assertTrue(this.grid.getNearestNeighbors(50, 50).get(0).equals(b));
        assertTrue(this.grid.getNearestNeighbors(50, 50).get(1).equals(c));
        assertTrue(this.grid.getNearestNeighbors(50, 50).get(2).equals(d));
        assertTrue(this.grid.getNearestNeighbors(50, 50).get(3).equals(a));
        assertTrue(this.grid.getNearestNeighbors(50, 50).size() == 4);
    }
    
}
