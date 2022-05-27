package com.javarush.games.snake.view;

import com.javarush.engine.cell.*;
import com.javarush.games.snake.SnakeGame;

public class Message {
    private int x;
    private int y;
    private String[] symbols;
    private Color color;
    private Color bgColor;
    private int textSize;


    public Message(int x, int y, String text, Color color) {
        this.symbols = specialSplit(text);
        this.color = color;
        this.bgColor = Color.BLACK;
        this.textSize = 90;

        if (x >= 0 && x < SnakeGame.HEIGHT) {
            this.x = x;
        } else {
            int padding = symbols.length / 2;
            this.x = SnakeGame.WIDTH / 2 - padding;
        }
        if (y >= 0 && y < SnakeGame.WIDTH) {
            this.y = y;
        } else {

            this.y = SnakeGame.WIDTH / 2;
        }
    }

    public void draw() {
        try {
            for (int i = 0; i < symbols.length; i++) {
                SnakeGame.game.setCellValueEx((i + this.x), this.y, bgColor, symbols[i], color, textSize);
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
    }

    private String[] specialSplit(String text) {
        String[] result = text.toUpperCase().split("");
        if (!result[0].equals("")) {
            return result;
        } else {
            String[] resultJavaRush = new String[result.length - 1];
            System.arraycopy(result, 1, resultJavaRush, 0, resultJavaRush.length);
            return resultJavaRush;
        }
    }
}
