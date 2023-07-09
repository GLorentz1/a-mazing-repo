package com.glore.maze;

public class MazeMain {
    public static void main(String[] args) {
        Grid grid = new Grid(3);
        // Print the details of each cell
        for (Cell cell : grid.cells()) {
            System.out.println("Cell: Row - " + cell.row() + ", Column - " + cell.column());
        }
    }
}
