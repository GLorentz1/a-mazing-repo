package com.glore.maze.fixtures;

import com.glore.maze.Grid;
import com.glore.maze.Cell.Wall;

public class GridFixture {
    public static Grid aValidGrid() {
        Grid grid = new Grid(3);
        grid.removeWallAt(0,0,Wall.BOTTOM);
        grid.removeWallAt(1,0,Wall.RIGHT);
        grid.removeWallAt(1,1,Wall.TOP);
        grid.removeWallAt(0,1,Wall.RIGHT);
        grid.removeWallAt(0,2,Wall.BOTTOM);
        grid.removeWallAt(1,2,Wall.BOTTOM);
        grid.removeWallAt(2,1,Wall.RIGHT);
        grid.removeWallAt(2,0,Wall.RIGHT);
        grid.setGoalX(2);
        grid.setGoalY(1);
        return grid;
    }
}
