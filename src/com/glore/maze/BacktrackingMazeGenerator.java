package com.glore.maze;

import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

import com.glore.maze.Cell.Wall;

public class BacktrackingMazeGenerator implements MazeGenerator {

    @Override
    public Grid generate(Integer size) {
        Grid maze = new Grid(size);
        Cell cell = maze.cellAt(0,0);
        Stack<Cell> stack = new Stack<Cell>();

        do {
            if(cell.visited() == false) {
                cell.setVisited(true);
                stack.push(cell);
            }
            
            List<Cell> unvisitedNeighbors = maze.positionalNeighbors(cell).stream().filter(c -> c.visited() == false).collect(Collectors.toList());
            if(unvisitedNeighbors.isEmpty()) {
                cell = stack.pop();
            } else {
                Cell chosenNeighbor = chooseRandom(unvisitedNeighbors);
                Wall wallToRemove = determineWallToRemove(cell, chosenNeighbor);
                maze.removeWallAt(cell.row(), cell.column(), wallToRemove);
                cell = chosenNeighbor;
            }
        } while (!stack.isEmpty());

        for(Cell c : maze.cells()) {
            c.setVisited(false);
        }

        return maze;
    }

    private <T> T chooseRandom(List<T> list) {
        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }

    private Wall determineWallToRemove(Cell cell, Cell neighbor) {
        Integer yDiff = cell.row() - neighbor.row();
        Integer xDiff = cell.column() - neighbor.column();

        if (xDiff == -1) {
            return Wall.RIGHT;
        } else if (xDiff == 1) {
            return Wall.LEFT;
        } else if (yDiff == 1) {
            return Wall.TOP;
         } else {
            return Wall.BOTTOM;
        }
    }
}
