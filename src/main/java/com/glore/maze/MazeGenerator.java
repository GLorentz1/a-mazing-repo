package com.glore.maze;

public interface MazeGenerator {
    Grid generate(Integer size);
    void setVisualizer(MazeVisualizer visualizer);
}
