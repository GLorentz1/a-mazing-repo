package com.glore.maze;

import com.glore.maze.generator.BacktrackingMazeGenerator;
import com.glore.maze.generator.RandomizedKruskalGenerator;
import com.glore.maze.generator.WilsonsMazeGenerator;
import com.glore.maze.solver.FloodMazeSolver;

public class MazeMain {
    public static void main(String[] args) {
        Integer size = 20;

        WilsonsMazeGenerator generator = new WilsonsMazeGenerator();        
        PlayerMovementController controller = new PlayerMovementController(new Player());
        FloodMazeSolver solver = new FloodMazeSolver();
        MazeManager manager = 
                            new MazeManager.Builder()
                                .withController(controller)
                                .withGenerator(generator, size)
                                .withSolver(solver)
                                .withMazeGenerationVisualization(true)
                                .build();
        
        manager.manage();
    }
}
