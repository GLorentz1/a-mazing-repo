package com.glore.maze;

public class MazeMain {
    public static void main(String[] args) {
        BacktrackingMazeGenerator generator = new BacktrackingMazeGenerator();
        GUIMazeVisualizer visualizer = new GUIMazeVisualizer();

        Grid grid = generator.generate(25);

        visualizer.visualize(grid);
    }
}
