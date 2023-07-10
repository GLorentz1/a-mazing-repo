package com.glore.maze;

import javax.swing.*;

import com.glore.maze.Cell.Wall;

import java.awt.*;

public class GUIMazeVisualizer extends JFrame implements MazeVisualizer{
    private static Integer cellSize = 25;

    @Override
    public void visualize(Grid grid) {
        setTitle("Grid GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel gridPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(2*cellSize + cellSize*grid.dimension(), 2*cellSize + cellSize*grid.dimension());
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int offsetX = cellSize;
                int offsetY = cellSize;

                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(offsetX, offsetY, cellSize, cellSize);

                g.setColor(Color.cyan);
                g.fillRect(offsetX+(grid.dimension()-1)*cellSize, offsetY+(grid.dimension()-1)*cellSize, cellSize, cellSize);

                g.setColor(Color.BLACK);

                for (int i = 0; i < grid.dimension(); i++) {
                    for (int j = 0; j < grid.dimension(); j++) {
                        Cell cell = grid.cellAt(i, j);

                        int x = offsetX + j * cellSize;
                        int y = offsetY + i * cellSize;

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
        setContentPane(gridPanel);
        pack();
        setVisible(true);
    }
}
