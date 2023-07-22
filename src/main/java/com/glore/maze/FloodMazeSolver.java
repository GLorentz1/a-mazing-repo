package com.glore.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class FloodMazeSolver implements MazeSolver {
    private List<Stack<Cell>> paths;

    public FloodMazeSolver() {
        this.paths = new ArrayList<Stack<Cell>>();
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

                paths = newPaths;
            }
        } while (grid.cellAt(grid.goalY(), grid.goalX()).visited() == false);

        return paths.stream().filter(p -> p.peek().equals(grid.cellAt(grid.goalY(), grid.goalX()))).toList().get(0);
    }
}
