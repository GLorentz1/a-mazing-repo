package com.glore.maze;

import java.util.List;

import com.glore.maze.controller.MenuController;
import com.glore.maze.controller.PlayerMovementController;
import com.glore.maze.visualizer.GUIMazeVisualizer;

public class MazeManager {
    private final PlayerMovementController playerController;
    private final MenuController menuController;
    private final MazeVisualizer visualizer;
    private final MazeGenerator generator;
    private final MazeSolver solver;
    private final Grid grid;
    private final Player player;
    private final Integer size;

    private List<Cell> solution;

    public MazeManager(Builder builder) {
        this.playerController = builder.playerController;
        this.menuController = builder.menuController;
        this.generator = builder.generator;
        this.solver = builder.solver;
        this.size = builder.size;
        this.visualizer = new GUIMazeVisualizer(playerController, menuController);

        if(builder.visualizeGeneration) {
            generator.setVisualizer(this.visualizer);
        }

        if(builder.visualizeSolutionGeneration) {
            solver.setVisualizer(this.visualizer);
        }
        
        this.grid = builder.generator.generate(size);
        resetVisitedCells();

        this.player = builder.playerController.player();

        visualizer.setGrid(grid);
        playerController.setGrid(grid);
    }

    public PlayerMovementController getPlayerController() {
        return playerController;
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
        resetVisitedCells();

        Boolean hasWon = false;

        while (menuController.endMaze().equals(false) && !hasWon) {
            try {
                Thread.sleep(8);
                
                if(menuController.requestedToDisplay()) {
                    visualizer.visualizeSolution(solution);
                } else {
                    visualizer.visualizeSolution(null);  
                }

                hasWon = visualizeSolutionAndEndGameIfGoalIsReached();
            
                playerController.reset();
                menuController.reset();
                visualizer.visualize();

                sleepIfHasWon(hasWon);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        visualizer.finish();
    }

    private void sleepIfHasWon(Boolean hasWon) {
        if(hasWon) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Boolean visualizeSolutionAndEndGameIfGoalIsReached() {
        Cell currentCell = grid.cellAt(player.getPlayerY(), player.getPlayerX());
        if(grid.isGoalCell(currentCell)) {
            visualizer.visualizeSolution(solution);
            return true;
        }
        return false;
    }

    private void resetVisitedCells() {
        for(Cell cell : grid.cells()) {
            cell.setVisited(false);
        }
    }
    
    
    public static final class Builder {
        private PlayerMovementController playerController;
        private MenuController menuController;
        private MazeVisualizer visualizer;
        private MazeGenerator generator;
        private MazeSolver solver;
        private Integer size;
        private Boolean visualizeGeneration;
        private Boolean visualizeSolutionGeneration;

        public Builder() {}

        public Builder withPlayerController(PlayerMovementController controller) {
            this.playerController = controller;
            return this;
        }

        public Builder withMenuController(MenuController controller) {
            this.menuController = controller;
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

        public Builder withMazeGenerationVisualization(Boolean visualizeGeneration) {
            this.visualizeGeneration = visualizeGeneration;
            return this;
        }

        public Builder withMazeSolutionGenerationVisualization(Boolean visualizeSolutionGeneration) {
            this.visualizeSolutionGeneration = visualizeSolutionGeneration;
            return this;
        }

        public MazeManager build() {
            return new MazeManager(this);
        }
    }
}
