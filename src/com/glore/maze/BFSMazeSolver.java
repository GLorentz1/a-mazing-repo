package com.glore.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class BFSMazeSolver implements MazeSolver {
    private List<Cell> solution;
    private Stack<Cell> stack;

    public BFSMazeSolver() {
        this.stack = new Stack<Cell>();
        this.solution = new ArrayList<Cell>();
    }

    @Override
    public List<Cell> solve(Grid grid) {
        Cell cell = grid.cellAt(0,0);

        stack.push(cell);
        cell.setVisited(true);
        do {
            cell = stack.pop();
            solution.add(cell);

            List<Cell> unvisitedNeighbors = grid.neighborsFor(cell).stream().filter(c -> c.visited() == false).collect(Collectors.toList());
            for(Cell unvisitedNeighbor : unvisitedNeighbors) {
                unvisitedNeighbor.setVisited(true);
                stack.push(unvisitedNeighbor);
            }

        } while (!stack.isEmpty() && !grid.isGoalCell(cell));

        return solution;
    }
    
}
