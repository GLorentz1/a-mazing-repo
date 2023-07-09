package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.glore.maze.Cell;
import com.glore.maze.Cell.Wall;

public class CellTest {

    @Test
    public void removesWall() {
        Cell cell = new Cell(0,0);

        cell.breakWall(Wall.BOTTOM);
        cell.breakWall(Wall.LEFT);

        assertFalse(cell.walls().get(Wall.BOTTOM.ordinal()));
        assertFalse(cell.walls().get(Wall.LEFT.ordinal()));
    }

    @Test
    public void marksAsVisited() {
        Cell cell = new Cell(0,0);

        assertFalse(cell.visited());

        cell.setVisited(true);

        assertTrue(cell.visited());
    }
}
