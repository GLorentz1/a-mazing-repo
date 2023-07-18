package com.glore.maze;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.junit.Test;
import com.glore.maze.fixtures.GridFixture;


public class BFSMazeSolverTest {
    
    @Test
    public void findsSolutionForGrid() {
        Grid grid = GridFixture.aValidGrid();

        List<Cell> solutionCells = solver.solve(grid);

        assertTrue(solutionCells.get(solutionCells.size()-1).row() == grid.goalY() && solutionCells.get(solutionCells.size()-1).column() == grid.goalX());
    }

    @Test
    public void callsVisualizerToShowVisitedCells() {
        Grid grid = GridFixture.aValidGrid();

        List<Cell> solutionCells = solver.solve(grid);

        verify(visualizer, atLeast(solutionCells.size())).visualize();
    }

    private final MazeVisualizer visualizer = mock(MazeVisualizer.class);
    private final MazeSolver solver = new BFSMazeSolver(visualizer);

}
