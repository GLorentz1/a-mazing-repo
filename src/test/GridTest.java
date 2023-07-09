package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.glore.maze.Grid;

public class GridTest {

    @Test
    public void createsGridWithSquareOfSize() {
        Grid grid = new Grid(3);

        assertEquals(grid.cells().size(), 9);
    }
}
