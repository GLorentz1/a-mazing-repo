package com.glore.maze;

import javax.swing.*;

import com.glore.maze.Cell.Wall;

import java.awt.*;
import java.util.List;

public class GUIMazeVisualizer extends JFrame implements MazeVisualizer{
    private static Integer cellSize = 20;
    private JPanel gridPanel;
    private List<Cell> solution;

    public GUIMazeVisualizer(Grid grid) {
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
        setTitle("Grid GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(gridPanel);
        pack();
        setVisible(true);
    }

    @Override
    public void visualizeSolution(List<Cell> path) {
        solution = path;
        gridPanel.repaint();
    }
}
