package com.glore.maze;

import java.util.List;

public class MazeManager {
    private final PlayerMovementController controller;
    private final MazeVisualizer visualizer;
    private final MazeGenerator generator;
    private final MazeSolver solver;
    private final Grid grid;
    private final Player player;
    private final Integer size;

    private List<Cell> solution;


    public MazeManager(Builder builder) {
        this.controller = builder.controller;
        this.visualizer = builder.visualizer;
        this.generator = builder.generator;
        this.solver = builder.solver;
        this.size = builder.size;
        this.grid = builder.generator.generate(size);
        this.player = builder.controller.player();
    }

    public PlayerMovementController getController() {
        return controller;
    }

    public MazeVisualizer getVisualizer() {
        return visualizer;
    }

    public MazeGenerator getGenerator() {
        return generator;
    }

    public MazeSolver getSolver() {
        return solver;
    }

    public Grid getGrid() {
        return grid;
    }

    public Player getPlayer() {
        return player;
    }

    public Integer getSize() {
        return size;
    }

    public List<Cell> getSolution() {
        if(solution == null) {
            solution = solver.solve(grid);
        }
        return solution;
    }

    public void manage() {
        getSolution();

        while (true) {
            try {
                Thread.sleep(64);
                visualizer.visualize();
                controller.reset();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public static final class Builder {
        private PlayerMovementController controller;
        private MazeVisualizer visualizer;
        private MazeGenerator generator;
        private MazeSolver solver;
        private Integer size;

        public Builder() {}

        public Builder withController(PlayerMovementController controller) {
            this.controller = controller;
            return this;
        }

        public Builder withVisualizer(MazeVisualizer visualizer) {
            this.visualizer = visualizer;
            return this;
        }
        
        public Builder withGenerator(MazeGenerator generator, Integer size) {
            this.generator = generator;
            this.size = size;
            return this;
        }

        public Builder withSolver(MazeSolver solver) {
            this.solver = solver;
            return this;
        }

        public MazeManager build() {
            return new MazeManager(this);
        }
    }
}
