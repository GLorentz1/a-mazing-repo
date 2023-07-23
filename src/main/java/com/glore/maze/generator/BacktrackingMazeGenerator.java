package com.glore.maze.generator;

import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

import com.glore.maze.Cell;
import com.glore.maze.Grid;
import com.glore.maze.MazeGenerator;
import com.glore.maze.MazeVisualizer;
import com.glore.maze.Cell.Wall;

public class BacktrackingMazeGenerator implements MazeGenerator {

    private MazeVisualizer visualizer = null;

    @Override
    public void setVisualizer(MazeVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    private void visualizeGrid(Grid grid) {
        if(visualizer != null) {
            visualizer.visualizeGrid(grid);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

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
            visualizeGrid(maze);
        } while (!stack.isEmpty());

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
