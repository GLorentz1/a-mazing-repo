package com.glore.maze;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private int row;
    private int column;
    private List<Boolean> walls;
    private boolean visited;


    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.walls = new ArrayList<>(List.of(true, true, true, true));
        this.visited = false;
    }

    public Integer column() {
        return column;
    }

    public Integer row() {
        return row;
    }

    public Boolean visited() {
        return visited;
    }

    public List<Boolean> walls() {
        return walls;
    }

    public Boolean hasWall(Wall wall) {
        return this.walls.get(wall.ordinal());
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public void breakWall(Wall wall) {
        walls.set(wall.ordinal(), false);
    }

    public Boolean isTopRow() {
        return row == 0;
    }

    public Boolean isLeftmostColumn() {
        return column == 0;
    }

    public Boolean isBottomRow(Integer dimension) {
        return row == dimension-1;
    }

    public Boolean isRightmostColumn(Integer dimension) {
        return column == dimension-1;
    }

    public enum Wall {
        LEFT,
        TOP,
        RIGHT,
        BOTTOM
    }
}
