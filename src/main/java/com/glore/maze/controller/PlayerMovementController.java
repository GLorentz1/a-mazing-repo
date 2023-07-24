package com.glore.maze.controller;

import java.awt.event.*;

import com.glore.maze.Grid;
import com.glore.maze.Player;
import com.glore.maze.Cell.Wall;

public class PlayerMovementController  implements KeyListener {
    private Player player;
    private Grid grid;

    private boolean hasMoved = false;

    public PlayerMovementController(Player player) {
        this.player = player;
        this.grid = null;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public void reset() {
        this.hasMoved = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        movePlayer(keyCode);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public Player player() {
        return player;
    }

    private void movePlayer(Integer key) {
        if(!hasMoved) {
            grid.cellAt(player.getPlayerY(), player.getPlayerX()).setVisited(true);

            switch (key) {
                case KeyEvent.VK_W:
                    movePlayerUp();
                    break;
                case KeyEvent.VK_A:
                    movePlayerLeft();
                    break;
                case KeyEvent.VK_S:
                    movePlayerDown();
                    break;
                case KeyEvent.VK_D:
                    movePlayerRight();
                    break;
            }

            grid.cellAt(player.getPlayerY(), player.getPlayerX()).setVisited(true);
        }
    }

    private void movePlayerUp() {
        Integer playerY = player.getPlayerY();
        if(player.getPlayerY() - 1 >= 0) {
            if (!grid.cellAt(player.getPlayerY(), player.getPlayerX()).hasWall(Wall.TOP)) {
                playerY = player.getPlayerY() - 1;
            }
        }
        player.setPlayerY(playerY);
        hasMoved = true;
    }

    private void movePlayerLeft() {
        Integer playerX = player.getPlayerX();
        if(player.getPlayerX() - 1 >= 0) {
            if (!grid.cellAt(player.getPlayerY(), player.getPlayerX()).hasWall(Wall.LEFT)) {
                playerX = player.getPlayerX() - 1;
            }
        }
        player.setPlayerX(playerX);
        hasMoved = true;
    }

    private void movePlayerDown() {
        Integer playerY = player.getPlayerY();
        if(player.getPlayerY() + 1 < grid.dimension()) {
            if (!grid.cellAt(player.getPlayerY(), player.getPlayerX()).hasWall(Wall.BOTTOM)) {
                playerY = player.getPlayerY() + 1;
            }
        }
        player.setPlayerY(playerY);
        hasMoved = true;
    }

    private void movePlayerRight() {
        Integer playerX = player.getPlayerX();
        if(player.getPlayerX() + 1 < grid.dimension()) {
            if (!grid.cellAt(player.getPlayerY(), player.getPlayerX()).hasWall(Wall.RIGHT)) {
                playerX = player.getPlayerX() + 1;
            }
        }
        player.setPlayerX(playerX);
        hasMoved = true;
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }
}

