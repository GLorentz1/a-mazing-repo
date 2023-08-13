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

public class RecursiveDivisionGenerator implements MazeGenerator {

    private MazeVisualizer visualizer = null;

    @Override
    public void setVisualizer(MazeVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    private void visualizeGrid(Grid grid) {
        if(visualizer != null) {
            visualizer.visualizeGrid(grid);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Grid generate(Integer size) {
        Grid maze = new Grid(size);

        for(Cell cell : maze.cells()) {
            for (Wall wall : cell.removeableWalls(size)) {
                cell.breakWall(wall);
            }
        }

        visualizeGrid(maze);
        Chamber chamber = new Chamber(0, size-1, 0, size-1);
        recurse(maze, chamber);

        return maze;
    }

    private void recurse(Grid maze, Chamber chamber) {
        Random random = new Random();


        if(chamber.getEndingColumn() - chamber.getStartingColumn() >= 1 && chamber.getEndingRow() - chamber.getStartingRow() >= 1) {
            Direction direction = chooseRandom(List.of(Direction.HORIZONTAL, Direction.VERTICAL));

            if(direction.equals(Direction.VERTICAL)) {
                int randomIndex = random.nextInt(chamber.getStartingColumn(), chamber.getEndingColumn());

                int randomIndexToIgnore = random.nextInt(chamber.getStartingRow(), chamber.getEndingRow());

                for(Integer i = chamber.getStartingRow(); i <= chamber.getEndingRow(); i++) {
                    if(i != randomIndexToIgnore) {
                        Cell cell = maze.cellAt(i, randomIndex);
                        maze.addWallAt(cell.row(), cell.column(), Wall.RIGHT);
                    }
                }
                
                visualizeGrid(maze);

                Chamber newChamberOne = new Chamber(chamber.getStartingRow(), chamber.getEndingRow(), chamber.getStartingColumn(), randomIndex);
                Chamber newChamberTwo = new Chamber(chamber.getStartingRow(), chamber.getEndingRow(), randomIndex+1, chamber.getEndingColumn());
                
                recurse(maze, newChamberOne);
                recurse(maze, newChamberTwo);
            } else {
                int randomIndex = random.nextInt(chamber.getStartingRow(), chamber.getEndingRow());
                int randomIndexToIgnore = random.nextInt(chamber.getStartingColumn(), chamber.getEndingColumn());


                for(Integer i = chamber.getStartingColumn(); i <= chamber.getEndingColumn(); i++) {
                    if (i != randomIndexToIgnore) {
                        Cell cell = maze.cellAt(randomIndex, i);
                        maze.addWallAt(cell.row(), cell.column(), Wall.BOTTOM);
                    }
                }

                visualizeGrid(maze);

                Chamber newChamberOne = new Chamber(chamber.getStartingRow(), randomIndex, chamber.getStartingColumn(), chamber.getEndingColumn());
                Chamber newChamberTwo = new Chamber(randomIndex+1, chamber.getEndingRow(), chamber.getStartingColumn(), chamber.getEndingColumn());
                
                recurse(maze, newChamberOne);
                recurse(maze, newChamberTwo);
            }
        } 
    }

    private <T> T chooseRandom(List<T> list) {
        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }

    private enum Direction {
        VERTICAL,
        HORIZONTAL
    }

    private static class Chamber {
        private final Integer startingRow;
        private final Integer endingRow;
        private final Integer startingColumn;
        private final Integer endingColumn;

        public Chamber(Integer startingRow, Integer endingRow, Integer startingColumn, Integer endingColumn) {
            this.startingRow = startingRow;
            this.endingRow = endingRow;
            this.startingColumn = startingColumn;
            this.endingColumn = endingColumn;
        }

        public Integer getStartingRow() {
            return startingRow;
        }

        public Integer getEndingRow() {
            return endingRow;
        }

        public Integer getStartingColumn() {
            return startingColumn;
        }

        public Integer getEndingColumn() {
            return endingColumn;
        }       

    }
}
