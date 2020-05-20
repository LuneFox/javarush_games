package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;

public class InputEvent {
    private MoonLanderGame game;

    InputEvent(MoonLanderGame game) {
        this.game = game;
    }

    public void keyPress(Key key) {
        switch (Screen.getCurrent()) {
            case COLOR_PAINTER: {
                break;
            }
        }
    }

    public void leftClick(int x, int y) {
        if (clickOutOfBounds(x, y)) {
            return;
        }
        switch (Screen.getCurrent()) {
            case COLOR_PAINTER: {
                if (x > 7 && y > 7) {
                    game.spritePainterTool.pasteColor(x, y);
                }
                break;
            }
        }
    }

    public void rightClick(int x, int y) {
        if (clickOutOfBounds(x, y)) {
            return;
        }
        System.out.println(x + ", " + y);
        switch (Screen.getCurrent()) {
            case COLOR_PAINTER: {
                game.spritePainterTool.copyColor(x, y);
                break;
            }
        }
    }

    public boolean clickOutOfBounds(int x, int y) {
        return (x < 0 || y < 0 || x > MoonLanderGame.WIDTH - 1 || y > MoonLanderGame.HEIGHT - 1);
    }
}
