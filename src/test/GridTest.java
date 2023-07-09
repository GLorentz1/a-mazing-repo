package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.glore.maze.Grid;
import com.glore.maze.Cell.Wall;

public class GridTest {

    @Test
    public void createsGridWithSquareOfSize() {
        Grid grid = new Grid(3);

        assertEquals(grid.cells().size(), 9);
    }

    @Test
    public void removesWallFromCellAndNeighbhor() {
        Grid grid = new Grid(3);

        grid.removeWallAt(1,1, Wall.BOTTOM);
        grid.removeWallAt(1,1, Wall.LEFT);
        grid.removeWallAt(1,1, Wall.RIGHT);
        grid.removeWallAt(1,1, Wall.TOP);

        assertFalse(grid.cellAt(1,1).hasWall(Wall.BOTTOM));
        assertFalse(grid.cellAt(1,1).hasWall(Wall.LEFT));
        assertFalse(grid.cellAt(1,1).hasWall(Wall.RIGHT));
        assertFalse(grid.cellAt(1,1).hasWall(Wall.TOP));

        assertFalse(grid.cellAt(2,1).hasWall(Wall.TOP));
        assertFalse(grid.cellAt(1,0).hasWall(Wall.RIGHT));
        assertFalse(grid.cellAt(1,2).hasWall(Wall.LEFT));
        assertFalse(grid.cellAt(0,1).hasWall(Wall.BOTTOM));
    }

    @Test
    public void doesNotRemoveWallIfCellIsAtBorder() {
        Grid grid = new Grid(3);
        
        grid.removeWallAt(0, 0, Wall.LEFT);
        grid.removeWallAt(0, 0, Wall.TOP);
        grid.removeWallAt(2, 2, Wall.RIGHT);
        grid.removeWallAt(2, 2, Wall.BOTTOM);

        assertTrue(grid.cellAt(0,0).hasWall(Wall.TOP));
        assertTrue(grid.cellAt(0,0).hasWall(Wall.LEFT));
        assertTrue(grid.cellAt(2,2).hasWall(Wall.RIGHT));
        assertTrue(grid.cellAt(2,2).hasWall(Wall.BOTTOM));
    }
}
