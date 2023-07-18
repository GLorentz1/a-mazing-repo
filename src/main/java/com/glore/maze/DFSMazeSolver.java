package com.glore.maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DFSMazeSolver implements MazeSolver {

    private List<Cell> solution;
    private Map<Cell,Cell> successors;
    private MazeVisualizer visualizer;

        public DFSMazeSolver(MazeVisualizer visualizer) {
        this.solution = new ArrayList<Cell>();
        this.successors = new HashMap<>();
        this.visualizer = visualizer;
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
                visualizeVisited();
            } else {
                cell = successors.get(cell);
            }
        } while (successors.get(cell) != null && !grid.isGoalCell(cell));

        return buildPath(successors, grid.cellAt(grid.goalY(), grid.goalX()));
    }

    private void visualizeVisited() {
        if (this.visualizer != null) {
            visualizer.visualize();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Failure on thread sleep");
            }
        }
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
