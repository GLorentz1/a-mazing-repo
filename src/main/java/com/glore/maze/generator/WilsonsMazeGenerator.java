package com.glore.maze.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.glore.maze.Cell;
import com.glore.maze.Cell.Wall;
import com.glore.maze.Grid;
import com.glore.maze.MazeGenerator;
import com.glore.maze.MazeVisualizer;

public class WilsonsMazeGenerator implements MazeGenerator {
    private MazeVisualizer visualizer = null;

    @Override
    public void setVisualizer(MazeVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    private void visualizeGrid(Grid grid) {
        if(visualizer != null) {
            visualizer.visualizeGrid(grid);
            try {
                Thread.sleep(32);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Grid generate(Integer size) {
        Grid grid = new Grid(size);

        List<Cell> cellsInMaze = new ArrayList<>();
        List<Cell> cellsInCurrentPath = new ArrayList<>();
        List<Cell> cellsNotInMaze = new ArrayList<>();

        cellsNotInMaze.addAll(grid.cells());

        Cell cell = chooseRandom(cellsNotInMaze);
        cellsNotInMaze.remove(cell);
        cellsInMaze.add(cell);

        Cell initialGoalCell = chooseRandom(cellsNotInMaze);
        cellsInMaze.add(initialGoalCell);
        cellsNotInMaze.remove(initialGoalCell);

        cellsInCurrentPath.add(0, cell);

        while(cellsNotInMaze.size() > 0) {
            List<Cell> aptNeighbors = grid.positionalNeighbors(cell);
            Cell randomNeighbor = chooseRandom(aptNeighbors);

            if(cellsInMaze.contains(randomNeighbor)) {
                cellsInMaze.addAll(cellsInCurrentPath);
                cellsNotInMaze.removeAll(cellsInCurrentPath);
                cellsInCurrentPath.add(randomNeighbor);

                for(Integer z=0; z < cellsInCurrentPath.size() - 1; z++){
                    Cell cell1 = cellsInCurrentPath.get(z);
                    Cell cell2 = cellsInCurrentPath.get(z+1);

                    Wall wallToRemove = determineWallToRemove(cell1, cell2);
                    grid.removeWallAt(cell1.row(), cell1.column(), wallToRemove);
                }
                
                cellsInCurrentPath = new ArrayList<>();
                if (cellsNotInMaze.size() > 0) {
                    cell = chooseRandom(cellsNotInMaze);
                    cellsInCurrentPath.add(0, cell);
                }

                visualizeGrid(grid);
            } else {
                if (cellsInCurrentPath.contains(randomNeighbor)) {
                    Integer indexOfNeighbor = cellsInCurrentPath.indexOf(randomNeighbor);
                    cellsInCurrentPath = cellsInCurrentPath.subList(0, indexOfNeighbor+1);
                    cell = randomNeighbor;
                } else {
                    cellsInCurrentPath.add(randomNeighbor);
                    cell = randomNeighbor;
                }
            }
        }

        return grid;
    }


    private <T> T chooseRandom(List<T> list) {

        if (list.size() == 0) {
            Integer a = 0;
        }
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
