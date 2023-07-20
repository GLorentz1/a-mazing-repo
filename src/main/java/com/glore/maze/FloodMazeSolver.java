package com.glore.maze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class FloodMazeSolver implements MazeSolver {
    private List<Stack<Cell>> paths;
    private MazeVisualizer visualizer;

    public FloodMazeSolver(MazeVisualizer visualizer) {
        this.paths = new ArrayList<Stack<Cell>>();
        this.visualizer = visualizer;
    }

    @Override
    public List<Cell> solve(Grid grid) {
        do {
            if (paths.size() == 0) {
                Cell cell = grid.cellAt(0,0);
                cell.setVisited(true);
                Stack<Cell> newPath = new Stack<Cell>();
                newPath.push(cell);
                paths.add(newPath);
            } else {
                List<Stack<Cell>> newPaths = new ArrayList<Stack<Cell>>();
                Set<Cell> topCells = new HashSet<>();

                for (Stack<Cell> path : paths) {
                    Cell topCell = path.peek();

                    List<Cell> unvisitedNeighbors = grid.reachableNeighbors(topCell).stream().filter(c -> c.visited() == false).collect(Collectors.toList());
                    if(unvisitedNeighbors.size() > 0) {
                        for (Cell neighbor : unvisitedNeighbors) {
                            Stack<Cell> newPath = new Stack<>();
                            neighbor.setVisited(true);
                            newPath.addAll(path);
                            newPath.push(neighbor);
                            newPaths.add(newPath);
                        }
                    }                 
                }

                visualizeVisited();

                List<Stack<Cell>> prunedNewPaths = new ArrayList<Stack<Cell>>();
                for (Stack<Cell> newPath : newPaths) {
                    Cell topCellFromNewPath = newPath.peek();

                    if (!topCells.contains(topCellFromNewPath)) {
                        prunedNewPaths.add(newPath);
                        topCells.add(topCellFromNewPath);
                    }
                }
                paths = prunedNewPaths;
            }
        } while (grid.cellAt(grid.goalY(), grid.goalX()).visited() == false);

        return paths.stream().filter(p -> p.peek().equals(grid.cellAt(grid.goalY(), grid.goalX()))).toList().get(0);
    }

    private void visualizeVisited() {
        if (this.visualizer != null) {
            visualizer.visualize();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                System.out.println("Failure on thread sleep");
            }
        }
    }

}
