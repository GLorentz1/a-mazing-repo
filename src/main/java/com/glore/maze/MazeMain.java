package com.glore.maze;

import com.glore.maze.controller.MenuController;
import com.glore.maze.controller.PlayerMovementController;
import com.glore.maze.generator.BacktrackingMazeGenerator;
import com.glore.maze.generator.RandomizedKruskalGenerator;
import com.glore.maze.generator.WilsonsMazeGenerator;
import com.glore.maze.solver.BFSMazeSolver;
import com.glore.maze.solver.DFSMazeSolver;
import com.glore.maze.solver.FloodMazeSolver;

public class MazeMain {
    public static void main(String[] args) {
        Integer size = 10;

        RandomizedKruskalGenerator generator = new RandomizedKruskalGenerator();        
        PlayerMovementController controller = new PlayerMovementController(new Player());
        FloodMazeSolver solver = new FloodMazeSolver();
        MazeManager manager = 
                            new MazeManager.Builder()
                                .withPlayerController(controller)
                                .withMenuController(new MenuController())
                                .withGenerator(generator, size)
                                .withSolver(solver)
                                .withMazeGenerationVisualization(false)
                                .build();
        
        manager.manage();
    }
}
