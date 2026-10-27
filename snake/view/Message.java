package com.javarush.games.snake.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;

public class Message {
    private static final SnakeGame game = SnakeGame.getInstance();
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final int TEXT_SIZE = 90;

    private final int x;
    private final int y;
    private final char[] symbols;
    private final Color color;

    public static void print(int x, int y, String text, Color color) {
        new Message(x, y, text, color).draw();
    }

    public Message(int x, int y, String text, Color color) {
        this.symbols = text.toUpperCase().toCharArray();
        this.color = color;
        this.x = resolveX(x, symbols.length);
        this.y = resolveY(y);
    }

    private static int resolveX(int x, int textLength) {
        if (x >= 0 && x < SnakeGame.SIZE) return x;

        return (SnakeGame.SIZE / 2) - (textLength / 2);
    }

    private static int resolveY(int y) {
        if (y >= 0 && y < SnakeGame.SIZE) return y;

        return SnakeGame.SIZE / 2;
    }

    public void draw() {
        if (x < 0 || x + symbols.length > SnakeGame.SIZE) return;

        for (int i = 0; i < symbols.length; i++) {
            final String symbol = String.valueOf(symbols[i]);
            game.setCellValueEx(x + i, y, BACKGROUND_COLOR, symbol, color, TEXT_SIZE);
        }
    }
}
