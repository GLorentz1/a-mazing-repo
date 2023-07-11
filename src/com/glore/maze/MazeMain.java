package com.glore.maze;

import java.util.List;

public class MazeMain {
    public static void main(String[] args) {
        BacktrackingMazeGenerator generator = new BacktrackingMazeGenerator();
        
        Grid grid = generator.generate(30);

        GUIMazeVisualizer visualizer = new GUIMazeVisualizer(grid);
        BFSMazeSolver solver = new BFSMazeSolver();

        List<Cell> solution = solver.solve(grid);

        visualizer.visualize();
        visualizer.visualizeSolution(solution);
    }
}
