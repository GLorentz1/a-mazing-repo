package com.glore.maze.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import com.glore.maze.Cell;
import com.glore.maze.Cell.Wall;
import com.glore.maze.Grid;
import com.glore.maze.MazeGenerator;
import com.glore.maze.MazeVisualizer;

public class RandomizedKruskalGenerator implements MazeGenerator {

    private MazeVisualizer visualizer = null;

    @Override
    public void setVisualizer(MazeVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    private void visualizeGrid(Grid grid) {
        if(visualizer != null) {
            visualizer.visualizeGrid(grid);
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Grid generate(Integer size) {
        Grid grid = new Grid(size);
        List<Set<Cell>> sets = new ArrayList<Set<Cell>>();
        Map<Cell,List<Wall>> cellWalls = new HashMap<>();

        for (Cell cell : grid.cells()) {
            Set<Cell> set = new HashSet<>();
            set.add(cell);
            sets.add(0, set);
            cellWalls.put(cell, cell.removeableWalls(size));
        }

        while(sets.size() > 1) {
            Cell cell = chooseRandom(cellWalls.keySet());
            Wall toRemove = chooseRandom(cellWalls.get(cell));
            Cell neighbor = getNeighbor(grid, cell, toRemove);

            Set<Cell> cellSet = getSetForCell(sets, cell);
            Set<Cell> neighborSet = getSetForCell(sets, neighbor);

            if (!cellSet.equals(neighborSet)) {
                sets.remove(cellSet);
                sets.remove(neighborSet);
                Set<Cell> newSet = new HashSet<>(cellSet);
                newSet.addAll(neighborSet);
                sets.add(newSet);

                grid.removeWallAt(cell.row(), cell.column(), toRemove);

                if (cell.removeableWalls(size).size() > 0) {
                    cellWalls.put(cell, cell.removeableWalls(size));
                } else {
                    cellWalls.remove(cell);
                }

                if (neighbor.removeableWalls(size).size() > 0) {
                    cellWalls.put(cell, neighbor.removeableWalls(size));
                } else {
                    cellWalls.remove(neighbor);
                }
            }

            visualizeGrid(grid);
        }
        

        return grid;
    }

    private Set<Cell> getSetForCell(List<Set<Cell>> sets, Cell cell) {
        for(Set<Cell> set : sets) {
            if (set.contains(cell)) {
                return set;
            }
        }

        return null;
    }

    private <T> T chooseRandom(Collection<T> collection) {
        try {
            Random random = new Random();
            int randomIndex = random.nextInt(collection.size());
            Iterator<T> iterator = collection.iterator();
            for (int i = 0; i < randomIndex; i++) {
                iterator.next();
            }
            return iterator.next();
        } catch (IllegalArgumentException e) {
            return null;
        }
        
    }

    private Cell getNeighbor(Grid grid, Cell cell, Wall wall) {
        Cell neighbor = null;
        if (wall.equals(Wall.BOTTOM)) {
            neighbor = grid.cellAt(cell.row()+1, cell.column());
        } else if (wall.equals(Wall.LEFT)) {
            neighbor =  grid.cellAt(cell.row(), cell.column()-1);
        } else if (wall.equals(Wall.RIGHT)) {
            neighbor = grid.cellAt(cell.row(), cell.column()+1);
        }else if (wall.equals(Wall.TOP)) {
            neighbor = grid.cellAt(cell.row()-1, cell.column());
        }
        return neighbor;
    }    
}
