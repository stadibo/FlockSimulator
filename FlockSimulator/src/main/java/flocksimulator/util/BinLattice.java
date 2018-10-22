package flocksimulator.util;

import java.util.RandomAccess;

/**
 * Bin-Lattice spatial subdivision data structure described in this paper:
 * https://www.red3d.com/cwr/papers/2000/pip.pdf implemented for 2D neighbor-
 * queries.
 *
 * @author peje
 */
public class BinLattice<T> implements RandomAccess {

    private FlockList<T>[][] grid;  // 2D array of lists
    private int rows, cols; // amount of columns and rows
    private int scale;  // Size of a grid cell

    public BinLattice(int width, int height, int scale) {
        if (scale <= 0) {
            throw new IllegalArgumentException("Illegal scale: " + scale);
        }
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Illegal resolution: " + width + "x" + height);
        }
        this.scale = scale;
        this.cols = width / scale + 1;
        this.rows = height / scale + 1;
    }

    /**
     * Initializes the grid as 2D array with empty lists
     */
    public void initGrid() {
        this.grid = new FlockList[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j] = new FlockList<T>();
            }
        }
    }

    /**
     * Clear lists in grid of their elements
     */
    public void clearGrid() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j].clear();
            }
        }
    }

    /**
     * Add an element to a cell in the grid according to its position
     *
     * @param posX actual x position in simulation
     * @param posY actual y position in simulation
     * @param element to add to cell in grid
     */
    public void insert(int posX, int posY, T element) {
        int x = posX / scale;
        int y = posY / scale;
        this.grid[x][y].add(element);
    }

    /**
     * Query neighbors for specified position. The method gets the bin according
     * to the specified position. Adds the neighbors from the specified cell and
     * its eight neighboring cells to a list
     *
     * @param posX actual x position in simulation
     * @param posY actual y position in simulation
     * @return list of neighbors
     */
    public FlockList<T> getNearestNeighbors(int posX, int posY) {
        FlockList<T> neighbors = new FlockList(100);

        int x = posX / scale;
        int y = posY / scale;

        // Check own cell and eight neighboring cells
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
