/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flockSimulator.util;

import flockSimulator.domain.Agent;
import java.util.RandomAccess;

/**
 * Bin-Lattice spatial subdivision datastructure described in this paper:
 * https://www.red3d.com/cwr/papers/2000/pip.pdf implemented for 2D neighbor
 * queries
 *
 * @author peje
 */
public class BinLattice implements RandomAccess {

    private FlockList<Agent>[][] grid;
    private int rows, cols;
    private int scale;

    public BinLattice(int width, int height, int scale) {
        if (scale <= 0) {
            throw new IllegalArgumentException("Illegal scale: " + scale);
        }
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Illegal resolution: " + width + "x" + height);
        }
        this.scale = scale;
        this.cols = width / scale;
        this.rows = height / scale;
    }

    public void initGrid() {
        this.grid = new FlockList[cols + 1][rows + 1];
        for (int i = 0; i <= cols; i++) {
            for (int j = 0; j <= rows; j++) {
                grid[i][j] = new FlockList<Agent>();
            }
        }
    }

    public void clearGrid() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j].clear();
            }
        }
    }

    public void insert(int posX, int posY, Agent element) {
        int x = posX / scale;
        int y = posY / scale;
        this.grid[x][y].add(element);
    }

    public FlockList<Agent> getNearestNeighbors(int posX, int posY) {
        FlockList<Agent> neighbors = new FlockList(100);

        int x = posX / scale;
        int y = posY / scale;

        for (int n = -1; n <= 1; n++) {
            for (int m = -1; m <= 1; m++) {
                // Check boundaries: if outside grid -> don't get list
                if (x + n >= 0 && x + n < cols && y + m >= 0 && y + m < rows) {
                    for (int i = 0; i < grid[x + n][y + m].size(); i++) {
                        neighbors.add(grid[x + n][y + m].get(i));
                    }
                }
            }
        }

        return neighbors;
    }

}
