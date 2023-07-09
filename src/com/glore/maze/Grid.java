package com.glore.maze;

import java.util.ArrayList;
import java.util.List;

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
}
