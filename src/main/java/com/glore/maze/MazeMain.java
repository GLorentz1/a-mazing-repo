package com.glore.maze;

import com.glore.maze.generator.BacktrackingMazeGenerator;
import com.glore.maze.solver.FloodMazeSolver;

public class MazeMain {
    public static void main(String[] args) {
        Integer size = 10;

        BacktrackingMazeGenerator generator = new BacktrackingMazeGenerator();        
        PlayerMovementController controller = new PlayerMovementController(new Player());
        FloodMazeSolver solver = new FloodMazeSolver();
        MazeManager manager = 
                            new MazeManager.Builder()
                                .withController(controller)
                                .withGenerator(generator, size)
                                .withSolver(solver)
                                .build();
        
        manager.manage();
    }
}
