/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flockSimulator.util;

import flockSimulator.domain.Agent;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
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
        Agent a = new Agent(25, 25, 12, 100, 4, 0.2, 1280, 720);
        this.grid.insert(40, 40, a);
    }
}
