package com.glore.maze;

import java.util.List;

public interface MazeVisualizer {
    void visualize();
    void visualizeSolution(List<Cell> path);
    void visualizeGrid(Grid grid);
    void setGrid(Grid grid);
}
