package com.glore.maze;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private List<Cell> cells;

    public Grid(Integer dimension) {
        this.cells = new ArrayList<>();

        for(Integer i = 0; i < dimension * dimension; i++) {
            this.cells.add(new Cell( (int) i / dimension, i % dimension));
        }
    }

    public List<Cell> cells() {
        return cells;
    }
    
}
