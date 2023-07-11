package com.glore.maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class BFSMazeSolver implements MazeSolver {
    private List<Cell> solution;
    private Stack<Cell> stack;
    private Map<Cell,Cell> successors;

    public BFSMazeSolver() {
        this.stack = new Stack<Cell>();
        this.solution = new ArrayList<Cell>();
        this.successors = new HashMap<>();
    }

    @Override
    public List<Cell> solve(Grid grid) {
        Cell cell = grid.cellAt(0,0);

        stack.push(cell);
        cell.setVisited(true);
        successors.put(cell, null);
        do {
            cell = stack.pop();

            List<Cell> unvisitedNeighbors = grid.reachableNeighbors(cell).stream().filter(c -> c.visited() == false).collect(Collectors.toList());
            for(Cell unvisitedNeighbor : unvisitedNeighbors) {
                unvisitedNeighbor.setVisited(true);
                stack.push(unvisitedNeighbor);
                successors.put(unvisitedNeighbor, cell);
            }

        } while (!stack.isEmpty() && !grid.isGoalCell(cell));

        return buildPath(successors, grid.cellAt(grid.dimension()-1, grid.dimension()-1));
    }

    private List<Cell> buildPath(Map<Cell, Cell> sucessorsMap, Cell goalCell) {
        Cell successor = successors.get(goalCell);
        while(successor != null) {
            solution.add(0, successor);
            successor = sucessorsMap.get(successor);
        }
        return solution;
    }
    
}
