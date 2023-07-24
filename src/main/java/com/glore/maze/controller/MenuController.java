package com.glore.maze.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuController implements KeyListener {
    private Boolean displaySolution = false;
    private Boolean hasFlipped = false;
    private Boolean end = false;

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE && !hasFlipped) {
            if(!this.hasFlipped) {
                this.displaySolution = !displaySolution;
                this.hasFlipped = true;
            }
        } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.end = true;
        }
    }

    public void reset() {
        this.hasFlipped = false;
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    public Boolean requestedToDisplay() {
        return this.displaySolution;
    }

    public Boolean endMaze() {
        return this.end;
    }
}
