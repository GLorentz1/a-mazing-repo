package com.glore.maze.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.glore.maze.Cell;
import com.glore.maze.Grid;
import com.glore.maze.MazeSolver;
import com.glore.maze.MazeVisualizer;

public class DFSMazeSolver implements MazeSolver {

    private List<Cell> solution;
    private Map<Cell,Cell> successors;
    private MazeVisualizer visualizer = null;

    @Override
    public void setVisualizer(MazeVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    private void visualizeGrid(Grid grid) {
        if(visualizer != null) {
            visualizer.visualizeGrid(grid);
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public DFSMazeSolver() {
        this.solution = new ArrayList<Cell>();
        this.successors = new HashMap<>();
    }

    @Override
    public List<Cell> solve(Grid grid) {
        Cell cell = grid.cellAt(0,0);

        cell.setVisited(true);
        successors.put(cell, null);
        do {
            List<Cell> unvisitedNeighbors = grid.reachableNeighbors(cell).stream().filter(c -> c.visited() == false).collect(Collectors.toList());

            if (unvisitedNeighbors.size() > 0) {
                Cell chosen = unvisitedNeighbors.get(0);
                chosen.setVisited(true);
                successors.put(chosen, cell);
                cell = chosen;
            } else {
                cell = successors.get(cell);
            }

            visualizeGrid(grid);
        } while (successors.get(cell) != null && !grid.isGoalCell(cell));

        return buildPath(successors, grid.cellAt(grid.goalY(), grid.goalX()));
    }

    private List<Cell> buildPath(Map<Cell, Cell> sucessorsMap, Cell goalCell) {
        solution.add(0, goalCell);
        Cell successor = successors.get(goalCell);
        while(successor != null) {
            solution.add(0, successor);
            successor = sucessorsMap.get(successor);
        }
        return solution;
    }
    
}
