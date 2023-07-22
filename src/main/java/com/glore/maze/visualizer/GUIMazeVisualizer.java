package com.glore.maze.visualizer;

import javax.swing.*;

import com.glore.maze.Cell;
import com.glore.maze.Grid;
import com.glore.maze.MazeVisualizer;
import com.glore.maze.Player;
import com.glore.maze.PlayerMovementController;
import com.glore.maze.Cell.Wall;

import java.awt.*;
import java.util.List;

public class GUIMazeVisualizer extends JFrame implements MazeVisualizer{
    private static Integer cellSize = 30;
    private JPanel gridPanel;
    private List<Cell> solution;
    private PlayerMovementController movementController;

    public GUIMazeVisualizer(Grid grid, PlayerMovementController movementController) {
        this.movementController = movementController;

        gridPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(2*cellSize + cellSize*grid.dimension(), 2*cellSize + cellSize*grid.dimension());
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                paintSolution(g);
                paintInitialCell(g);
                paintGoalCell(g);
                paintVisited(g, grid);
                paintGrid(g);
                paintPlayer(g, movementController);
            }

            private void paintSolution(Graphics g) {
                g.setColor(Color.MAGENTA);

                if(solution != null && !solution.isEmpty()) {
                    for (Cell cell : solution) {
                        int x = (cellSize + cell.column() * cellSize);
                        int y = (cellSize + cell.row() * cellSize);
                        g.fillRect(x, y, cellSize, cellSize);
                    }
                }
            }

            private void paintVisited(Graphics g, Grid grid) {
                if (solution == null || solution.isEmpty()) {
                    g.setColor(Color.ORANGE);
                    
                    for(Cell cell : grid.cells()) {
                        if(cell.visited()) {
                            int x = (cellSize + cell.column() * cellSize);
                            int y = (cellSize + cell.row() * cellSize);
                            g.fillRect(x, y, cellSize, cellSize);
                        }
                    }
                }
            }

            private void paintInitialCell(Graphics g) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(cellSize, cellSize, cellSize, cellSize);
            }

            private void paintPlayer(Graphics g, PlayerMovementController controller) {
                Player player = controller.player();
                Integer X = (cellSize + player.getPlayerX() * cellSize) + cellSize/2;
                Integer Y = (cellSize + player.getPlayerY() * cellSize) + cellSize/2;
                g.setColor(Color.red);
                g.fillRect(X, Y, cellSize/4, cellSize/4);
            }

            private void paintGoalCell(Graphics g) {
                g.setColor(Color.cyan);
                g.fillRect(cellSize+(grid.goalX())*cellSize, cellSize+(grid.goalY())*cellSize, cellSize, cellSize);
            }

            private void paintGrid(Graphics g) {
                g.setColor(Color.BLACK);
                for (int i = 0; i < grid.dimension(); i++) {
                    for (int j = 0; j < grid.dimension(); j++) {
                        Cell cell = grid.cellAt(i, j);

                        int x = cellSize + j * cellSize;
                        int y = cellSize + i * cellSize;

                        if (cell.hasWall(Wall.LEFT)) {
                            g.drawLine(x, y, x, y + cellSize);
                        }

                        if (cell.hasWall(Wall.TOP)) {
                            g.drawLine(x, y, x + cellSize, y);
                        }

                        if (cell.hasWall(Wall.RIGHT)) {
                            g.drawLine(x + cellSize, y, x + cellSize, y + cellSize);
                        }

                        if (cell.hasWall(Wall.BOTTOM)) {
                            g.drawLine(x, y + cellSize, x + cellSize, y + cellSize);
                        }
                    }
                }
            }
        };
    }


    @Override
    public void visualize() {
        SwingUtilities.invokeLater(() -> {
            setTitle("Grid GUI");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setContentPane(gridPanel);
            addKeyListener(movementController);
            pack();
            setVisible(true);
            setFocusable(true);
            requestFocusInWindow();
        });

    }

    @Override
    public void visualizeSolution(List<Cell> path) {
        solution = path;
        gridPanel.repaint();
    }
}
