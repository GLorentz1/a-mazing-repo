package com.glore.maze;

import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;
import com.glore.maze.fixtures.GridFixture;
import com.glore.maze.solver.BFSMazeSolver;


public class DFSMazeSolverTest {
    
    @Test
    public void findsSolutionForGrid() {
        Grid grid = GridFixture.aValidGrid();

        List<Cell> solutionCells = solver.solve(grid);

        assertTrue(solutionCells.get(solutionCells.size()-1).row() == grid.goalY() && solutionCells.get(solutionCells.size()-1).column() == grid.goalX());
    }

    private final MazeSolver solver = new BFSMazeSolver();

}
