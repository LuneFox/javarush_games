package com.javarush.games.snake.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.terrain.Terrain;
import com.javarush.games.snake.model.terrain.TerrainType;
import com.javarush.games.snake.view.Message;
import com.javarush.games.snake.view.View;

/** Редактор карты. */
public class MapEditorView extends View {
    private static final int PREVIEW_X = 1;
    private static final int PREVIEW_Y = 1;
    private static final int SAMPLE_LABEL_X = 3;
    private static final int SAMPLE_LABEL_Y = 1;

    private static MapEditorView instance;

    private TerrainType sample = TerrainType.FIELD;

    /** Возвращает единственный экземпляр. */
    public static MapEditorView getInstance() {
        if (instance == null) instance = new MapEditorView();
        return instance;
    }

    /** Рисует поле и выбранный образец. */
    @Override
    public void update() {
        drawField();
        drawSamplePreview();
    }

    /** Рисует поле. */
    private void drawField() {
        GameFieldView.getInstance().drawField();
    }

    /** Показывает выбранный образец. */
    private void drawSamplePreview() {
        game.getStage().putTerrain(PREVIEW_X, PREVIEW_Y, sample);
        Message.print(SAMPLE_LABEL_X, SAMPLE_LABEL_Y, Strings.getTerrainName(sample), Color.WHITE);
    }

    /** Выбирает следующий образец. */
    public void selectNextSample() {
        sample = shiftSample(1);
        update();
    }

    /** Выбирает предыдущий образец. */
    public void brushPrevious() {
        sample = shiftSample(-1);
        update();
    }

    /** Сдвигает образец по кругу. */
    private TerrainType shiftSample(int offset) {
        final TerrainType[] types = TerrainType.values();
        final int index = (sample.ordinal() + offset + types.length) % types.length;

        return types[index];
    }

    /** Кладёт текущий образец в клетку. */
    public void drawTerrain(int x, int y) {
        game.getStage().putTerrain(x, y, sample);
    }

    /** Копирует местность клетки в образец. */
    public void copyTerrain(int x, int y) {
        sample = game.getStage().getTerrain(x, y).getType();
    }

    /** Печатает карту в консоль. */
    public void printTerrain() {
        System.out.println("new int[][]{");

        for (int y = 0; y < SnakeGame.SIZE; y++) {
            for (int x = 0; x < SnakeGame.SIZE; x++) {
                final Terrain terrain = game.getStage().getTerrain(x, y);
                final int code = y < SnakeGame.HUD_ROWS ? Terrain.VOID : terrain.getType().ordinal();

                if (x == 0) {
                    System.out.print("{");
                }

                if (x < SnakeGame.SIZE - 1) {
                    System.out.print(code + ", ");
                } else {
                    System.out.print(code);
                    System.out.println("},");
                }
            }
        }

        System.out.println("});");
    }

    /** Печатает координаты клетки. */
    public void printCoordinate(int x, int y) {
        System.out.println(x + "," + y);
    }
}
