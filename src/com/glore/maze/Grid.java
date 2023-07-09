package com.glore.maze;

import java.util.ArrayList;
import java.util.List;

import com.glore.maze.Cell.Wall;

public class Grid {
    private Integer dimension; 
    private List<Cell> cells;

    public Grid(Integer dimension) {
        this.dimension = dimension;
        this.cells = new ArrayList<>();

        for(Integer i = 0; i < dimension * dimension; i++) {
            this.cells.add(new Cell( (int) i / dimension, i % dimension));
        }
    }

    public List<Cell> cells() {
        return cells;
    }
    
    public Integer dimension() {
        return this.dimension;
    }

    public Cell cellAt(Integer row, Integer column) {
        Integer index = this.dimension * row + column;
        return this.cells.get(index);
    }

    public List<Cell> neighborsFor(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        if(!cell.isBottomRow(dimension)) {
            neighbors.add(cellAt(cell.row()+1, cell.column()));
        } 

        if(!cell.isRightmostColumn(dimension)) {
            neighbors.add(cellAt(cell.row(), cell.column()+1)); 
        } 

        if(!cell.isLeftmostColumn()) {
            neighbors.add(cellAt(cell.row(), cell.column()-1)); 
        }
        
        if(!cell.isTopRow()) {
            neighbors.add(cellAt(cell.row()-1, cell.column())); 
        }
        return neighbors;
    }

    public void removeWallAt(Integer row, Integer column, Wall wall) {
        Cell cell = cellAt(row, column);

        if(wall.equals(Wall.BOTTOM)) {
            if(!cell.isBottomRow(dimension())) {
                cell.breakWall(Wall.BOTTOM);
                Cell neighborCell = cellAt(row+1, column);
                neighborCell.breakWall(Wall.TOP);
            }
        } else if(wall.equals(Wall.LEFT)) {
            if(!cell.isLeftmostColumn()) {
                cell.breakWall(Wall.LEFT);
                Cell neighborCell = cellAt(row, column-1);
                neighborCell.breakWall(Wall.RIGHT);
            }
        } else if(wall.equals(Wall.RIGHT)) {
            if(!cell.isRightmostColumn(dimension())) {
                cell.breakWall(Wall.RIGHT);
                Cell neighborCell = cellAt(row, column+1);
                neighborCell.breakWall(Wall.LEFT);
            }
        } else if(wall.equals(Wall.TOP)) {
            if(!cell.isTopRow()) {
                cell.breakWall(Wall.TOP);
                Cell neighborCell = cellAt(row-1, column);
                neighborCell.breakWall(Wall.BOTTOM);
            }
        }
    }
}
