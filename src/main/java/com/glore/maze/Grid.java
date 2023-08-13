package com.glore.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.glore.maze.Cell.Wall;

public class Grid {
    private Integer dimension; 
    private List<Cell> cells;
    private Integer goalX;
    private Integer goalY;

    public Grid(Integer dimension) {
        this.dimension = dimension;
        this.cells = new ArrayList<>();
        this.goalX = new Random().nextInt(dimension);
        this.goalY = new Random().nextInt(dimension);

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

    public Integer goalX() {
        return goalX;
    }

    public void setGoalX(Integer goalX) {
        if(goalX < this.dimension) {
            this.goalX = goalX;
        }
    }

    public Integer goalY() {
        return goalY;
    }

    public void setGoalY(Integer goalY) {
        if(goalY < this.dimension) {
            this.goalY = goalY;
        }
    }
    
    public Cell cellAt(Integer row, Integer column) {
        Integer index = this.dimension * row + column;
        return this.cells.get(index);
    }

    public Boolean isGoalCell(Cell cell) {
        return cell.row() == goalY && cell.column() == goalX;
    }


    public List<Cell> reachableNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();
        if(!cell.isBottomRow(dimension) && !cell.hasWall(Wall.BOTTOM)) {
            neighbors.add(cellAt(cell.row()+1, cell.column()));
        } 

        if(!cell.isRightmostColumn(dimension) && !cell.hasWall(Wall.RIGHT)) {
            neighbors.add(cellAt(cell.row(), cell.column()+1)); 
        } 

        if(!cell.isLeftmostColumn() && !cell.hasWall(Wall.LEFT)) {
            neighbors.add(cellAt(cell.row(), cell.column()-1)); 
        }
        
        if(!cell.isTopRow() && !cell.hasWall(Wall.TOP)) {
            neighbors.add(cellAt(cell.row()-1, cell.column())); 
        }
        return neighbors;
    }

    public List<Cell> positionalNeighbors(Cell cell) {
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

    public void addWallAt(Integer row, Integer column, Wall wall) {
        Cell cell = cellAt(row, column);
        cell.addWall(wall);

        if(wall.equals(Wall.BOTTOM)) {
            Cell neighborCell = cellAt(row+1, column);
            neighborCell.addWall(Wall.TOP);
            
        } else if(wall.equals(Wall.LEFT)) {
            Cell neighborCell = cellAt(row, column-1);
            neighborCell.addWall(Wall.RIGHT);
            
        } else if(wall.equals(Wall.RIGHT)) {
            Cell neighborCell = cellAt(row, column+1);
            neighborCell.addWall(Wall.LEFT);
            
        } else if(wall.equals(Wall.TOP)) {
            Cell neighborCell = cellAt(row-1, column);
            neighborCell.addWall(Wall.BOTTOM);
        }
    }
}
