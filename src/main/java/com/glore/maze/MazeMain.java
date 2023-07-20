package com.glore.maze;

public class MazeMain {
    public static void main(String[] args) {
        Integer size = 30;

        BacktrackingMazeGenerator generator = new BacktrackingMazeGenerator();        
        Grid grid = generator.generate(size);
        PlayerMovementController controller = new PlayerMovementController(new Player(), grid);
        GUIMazeVisualizer visualizer = new GUIMazeVisualizer(grid, controller);
        FloodMazeSolver solver = new FloodMazeSolver(visualizer);
        MazeManager manager = 
                            new MazeManager.Builder()
                                .withController(controller)
                                .withGenerator(generator, size)
                                .withVisualizer(visualizer)
                                .withSolver(solver)
                                .build();
        
        manager.manage();
    }
}
