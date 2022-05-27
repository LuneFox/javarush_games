package com.javarush.games.snake.view;

import com.javarush.engine.cell.*;
import com.javarush.games.snake.SnakeGame;

public class Message {
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final int TEXT_SIZE = 90;
    private static SnakeGame game;

    private final int x;
    private final int y;
    private final char[] charSequence;
    private final Color color;

    public static void setGame(SnakeGame game) {
        Message.game = game;
    }

    public static void print(int x, int y, String text, Color color) {
        Message message = new Message(x, y, text, color);
        message.draw();
    }

    public Message(int x, int y, String text, Color color) {
        this.charSequence = text.toUpperCase().toCharArray();
        this.color = color;
        this.x = (x >= 0) && (x < SnakeGame.SIZE) ? x : (SnakeGame.SIZE / 2) - (charSequence.length / 2);
        this.y = (y >= 0) && (y < SnakeGame.SIZE) ? y : (SnakeGame.SIZE / 2);
    }

    public void draw() {
        if (x + charSequence.length >= SnakeGame.SIZE || x < 0) return;

        for (int i = 0; i < charSequence.length; i++) {
            String symbol = String.valueOf(charSequence[i]);
            game.setCellValueEx(i + this.x, this.y, BACKGROUND_COLOR, symbol, color, TEXT_SIZE);
        }
    }
}
