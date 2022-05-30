package com.javarush.games.snake.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.Map;
import com.javarush.games.snake.model.terrain.Terrain;
import com.javarush.games.snake.view.Message;
import com.javarush.games.snake.view.View;

public class MapEditorView extends View {
    private static MapEditorView instance;
    private int brush;

    public static MapEditorView getInstance() {
        if (instance == null) instance = new MapEditorView();
        return instance;
    }

    @Override
    public void update() {
        Terrain terrain = Terrain.create(1, 1, brush);
        game.getMap().putTerrain(1, 1, terrain.getType());
        GameFieldView.getInstance().drawMap();
        Message.print(3, 1, terrain.getType().name(), Color.WHITE);
    }

    public void brushNext() {
        if (brush < 9) {
            brush++;
        } else {
            brush = 0;
        }
    }

    public void brushPrevious() {
        if (brush > 0) {
            brush--;
        } else {
            brush = 9;
        }
    }

    public void drawTerrain(int x, int y) {
        Terrain terrain = Terrain.create(x, y, brush);
        game.getMap().putTerrain(terrain.x, terrain.y, terrain.getType());
    }

    public void copyTerrain(int x, int y) {
        brush = game.getMap().getTerrain(x, y).getType().ordinal();
    }

    public void printTerrain() {
        Map map = game.getMap();

        System.out.println("new int[][]{");

        for (int y = 0; y < SnakeGame.SIZE; y++) {
            for (int x = 0; x < SnakeGame.SIZE; x++) {
                int number = (y < 4 ? 9 : map.getTerrain(x, y).getType().ordinal());

                if (x == 0) {
                    System.out.print("{");
                }

                if (x < SnakeGame.SIZE - 1) {
                    System.out.print(number + ", ");
                }

                if (x == SnakeGame.SIZE - 1) {
                    System.out.print(number);
                    System.out.println("},");
                }

            }
        }

        System.out.println("});");
    }

    public void printCoordinate(int x, int y) {
        System.out.println(x + "," + y);
    }
}
