package com.javarush.games.snake.controller.strategies;

import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.controller.ControlStrategy;
import com.javarush.games.snake.view.MenuSelector;
import com.javarush.games.snake.model.Phase;
import com.javarush.games.snake.view.impl.MapEditorView;

public class MapEditorControlStrategy implements ControlStrategy {
    private static MapEditorControlStrategy instance;

    public static MapEditorControlStrategy getInstance() {
        if (instance == null) instance = new MapEditorControlStrategy();
        return instance;
    }

    @Override
    public void leftClick(int x, int y) {
        if (SnakeGame.outOfBounds(x, y)) return;

        MapEditorView.getInstance().drawTerrain(x, y);
        Phase.proceed(Phase.MAP_EDITOR);
    }

    @Override
    public void rightClick(int x, int y) {
        if (SnakeGame.outOfBounds(x, y)) return;

        MapEditorView.getInstance().printCoordinate(x, y);
        MapEditorView.getInstance().copyTerrain(x, y);
        Phase.proceed(Phase.MAP_EDITOR);
    }

    @Override
    public void pressUp() {
        pressRight();
    }

    @Override
    public void pressDown() {
        pressLeft();
    }

    @Override
    public void pressRight() {
        MapEditorView.getInstance().selectNextSample();
        Phase.proceed(Phase.MAP_EDITOR);

    }

    @Override
    public void pressLeft() {
        MapEditorView.getInstance().brushPrevious();
        Phase.proceed(Phase.MAP_EDITOR);
    }

    @Override
    public void pressEnter() {
        MapEditorView.getInstance().printTerrain();
    }

    @Override
    public void pressEscape() {
        MenuSelector.loadLastPointerPosition();
        Phase.proceed(Phase.MAIN_MENU);
    }
}