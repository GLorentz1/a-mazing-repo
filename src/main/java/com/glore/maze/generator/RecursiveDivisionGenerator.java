package com.glore.maze.generator;

import java.util.ArrayList;
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
    private Random random = new Random();


    @Override
    public void setVisualizer(MazeVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    private void visualizeGrid(Grid grid) {
        if(visualizer != null) {
            visualizer.visualizeGrid(grid);
            try {
                Thread.sleep(10);
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
        if(chamber.getEndingColumn() - chamber.getStartingColumn() >= 1 && chamber.getEndingRow() - chamber.getStartingRow() >= 1) {
            Direction direction = chooseRandom(createProportionalListWithDirection(chamber));

            if(direction.equals(Direction.VERTICAL)) {
                int lineIndex = createRandomVerticalLine(maze, chamber);

                Chamber newChamberOne = new Chamber(chamber.getStartingRow(), chamber.getEndingRow(), chamber.getStartingColumn(), lineIndex);
                Chamber newChamberTwo = new Chamber(chamber.getStartingRow(), chamber.getEndingRow(), lineIndex+1, chamber.getEndingColumn());
                
                recurse(maze, newChamberOne);
                recurse(maze, newChamberTwo);
            } else {
                int lineIndex = createRandomHorizontalLine(maze, chamber);

                Chamber newChamberOne = new Chamber(chamber.getStartingRow(), lineIndex, chamber.getStartingColumn(), chamber.getEndingColumn());
                Chamber newChamberTwo = new Chamber(lineIndex+1, chamber.getEndingRow(), chamber.getStartingColumn(), chamber.getEndingColumn());
                
                recurse(maze, newChamberOne);
                recurse(maze, newChamberTwo);
            }
        } 
    }

    private int createRandomVerticalLine(Grid maze, Chamber chamber) {
        int randomIndex = random.nextInt(chamber.getStartingColumn(), chamber.getEndingColumn());

        int randomIndexToIgnore = random.nextInt(chamber.getStartingRow(), chamber.getEndingRow()+1);

        for(Integer i = chamber.getStartingRow(); i <= chamber.getEndingRow(); i++) {
            if(i != randomIndexToIgnore) {
                Cell cell = maze.cellAt(i, randomIndex);
                maze.addWallAt(cell.row(), cell.column(), Wall.RIGHT);
            }
        }
        
        visualizeGrid(maze);
        return randomIndex;
    }

    private int createRandomHorizontalLine(Grid maze, Chamber chamber) {
        int randomIndex = random.nextInt(chamber.getStartingRow(), chamber.getEndingRow());
        int randomIndexToIgnore = random.nextInt(chamber.getStartingColumn(), chamber.getEndingColumn()+1);


        for(Integer i = chamber.getStartingColumn(); i <= chamber.getEndingColumn(); i++) {
            if (i != randomIndexToIgnore) {
                Cell cell = maze.cellAt(randomIndex, i);
                maze.addWallAt(cell.row(), cell.column(), Wall.BOTTOM);
            }
        }

        visualizeGrid(maze);
        return randomIndex;
    }

    private List<Direction> createProportionalListWithDirection(Chamber chamber) {
        List<Direction> directionToChoose = new ArrayList<>();

        for(Integer i = 0; i < chamber.getEndingColumn() - chamber.getStartingColumn(); i++) {
            directionToChoose.add(Direction.VERTICAL);
        }

        for(Integer i = 0; i < chamber.getEndingRow() - chamber.getStartingRow(); i++) {
            directionToChoose.add(Direction.HORIZONTAL);
        }
        
        return directionToChoose;
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
