package com.javarush.games.snake.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.model.terrain.Terrain;
import com.javarush.games.snake.model.terrain.TerrainType;
import com.javarush.games.snake.view.Message;
import com.javarush.games.snake.view.View;

public class MapEditorView extends View {
    private static MapEditorView instance;
    private int sample;

    public static MapEditorView getInstance() {
        if (instance == null) instance = new MapEditorView();
        return instance;
    }

    @Override
    public void update() {
        drawField();
        drawSamplePreview();
    }

    private void drawField() {
        GameFieldView.getInstance().drawField();
    }

    private void drawSamplePreview() {
        Terrain terrain = Terrain.create(1, 1, sample);
        game.getStage().putTerrain(1, 1, terrain.getType());
        Message.print(3, 1, terrain.getType().name(), Color.WHITE);
    }

    public void selectNextSample() {
        sample = (sample < 9) ? (sample + 1) : 0;
        update();
    }

    public void brushPrevious() {
        sample = (sample > 0) ? (sample - 1) : 9;
        update();
    }

    public void drawTerrain(int x, int y) {
        Terrain terrain = Terrain.create(x, y, sample);
        final TerrainType type = terrain.getType();
        final Stage stage = game.getStage();
        stage.putTerrain(terrain.x, terrain.y, type);
    }

    public void copyTerrain(int x, int y) {
        final Stage stage = game.getStage();
        final Terrain terrain = stage.getTerrain(x, y);
        sample = terrain.getType().ordinal();
    }

    public void printTerrain() {
        System.out.println("new int[][]{");

        for (int y = 0; y < SnakeGame.SIZE; y++) {
            for (int x = 0; x < SnakeGame.SIZE; x++) {

                final Terrain terrain = game.getStage().getTerrain(x, y);
                final TerrainType type = terrain.getType();

                int number = (y < 4 ? 9 : type.ordinal());

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
