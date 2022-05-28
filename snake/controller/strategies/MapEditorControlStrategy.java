package com.javarush.games.snake.controller.strategies;

import com.javarush.games.snake.controller.ControlStrategy;
import com.javarush.games.snake.model.Menu;

public class MapEditorControlStrategy implements ControlStrategy {
    private static MapEditorControlStrategy instance;

    public static MapEditorControlStrategy getInstance() {
        if (instance == null) instance = new MapEditorControlStrategy();
        return instance;
    }

    @Override
    public void leftClick(int x, int y) {
        if (game.outOfBounds(x, y)) return;

        game.menu.drawTerrain(x, y);
        game.menu.displayMapEditor();
    }

    @Override
    public void rightClick(int x, int y) {
        if (game.outOfBounds(x, y)) return;

        game.menu.printCoordinate(x, y);
        game.menu.copyTerrain(x, y);
        game.menu.displayMapEditor();
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
        game.menu.brushNext();
        game.menu.displayMapEditor();
    }

    @Override
    public void pressLeft() {
        game.menu.brushPrevious();
        game.menu.displayMapEditor();
    }

    @Override
    public void pressEnter() {
        ControlStrategy.super.pressEnter();
    }

    @Override
    public void pressEscape() {
        Menu.Selector.setPointer(game.menu.lastPointerPosition);
        game.menu.displayMain();
    }
}