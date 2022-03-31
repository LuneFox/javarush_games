package com.javarush.games.moonlander;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.moonlander.controller.Controller;
import com.javarush.games.moonlander.model.Phase;
import com.javarush.games.moonlander.model.painter.Painter;

public class MoonLanderGame extends Game {
    public static final int WIDTH = 40;
    public static final int HEIGHT = 40;

    private static MoonLanderGame instance;
    private Controller controller;

    public Painter painter;

    @Override
    public void initialize() {
        showGrid(true);
        setScreenSize(WIDTH, HEIGHT);

        instance = this;
        controller = new Controller();
        painter = new Painter();
        painter.canvas.clear();
        Phase.setActive(Phase.PAINTER_EDITOR);
    }

    public static MoonLanderGame getInstance() {
        return instance;
    }


    @Override
    public void onMouseLeftClick(int x, int y) {
        controller.leftClick(x, y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        controller.rightClick(x, y);
    }

    @Override
    public void onKeyPress(Key key) {
        controller.pressKey(key);
    }
}