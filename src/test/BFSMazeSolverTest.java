package test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.glore.maze.BFSMazeSolver;
import com.glore.maze.Cell;
import com.glore.maze.Grid;
import com.glore.maze.MazeSolver;

import test.fixtures.GridFixture;

public class BFSMazeSolverTest {
    
    @Test
    public void findsSolutionForGrid() {
        Grid grid = GridFixture.aValidGrid();

        List<Cell> solutionCells = solver.solve(grid);

        assertTrue(solutionCells.get(solutionCells.size()-1) == grid.cellAt(grid.dimension()-1, grid.dimension()-1));
    }

    private final MazeSolver solver = new BFSMazeSolver();

}
