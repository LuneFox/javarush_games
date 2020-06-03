package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;

public class MoonLanderGame extends Game {
    // Screen size
    static final int WIDTH = 50;
    static final int HEIGHT = 50;

    // Painter
    private InputEvent inputEvent = new InputEvent(this);
    SpritePainterTool spritePainterTool = new SpritePainterTool(this);

    @Override
    public void initialize() {
        showGrid(true);
        setScreenSize(WIDTH, HEIGHT);
        spritePainterTool.display();
    }

    // INPUT EVENTS

    @Override
    public void onMouseLeftClick(int x, int y) {
        inputEvent.leftClick(x, y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        inputEvent.rightClick(x, y);
    }

    @Override
    public void onKeyPress(Key key) {
        inputEvent.keyPress(key);
    }
}