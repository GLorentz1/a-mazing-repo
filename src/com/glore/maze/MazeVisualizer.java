package com.glore.maze;

import java.util.List;

public interface MazeVisualizer {
    void visualize();
    void visualizeSolution(List<Cell> path);
}
