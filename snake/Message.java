package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class Message {
    private String[] symbols;
    private Color color;
    private Color bgColor;
    private int textSize;

    Message(String text, Color color) {
        // Create message object with given text and color
        this.symbols = specialSplit(text);
        this.color = color;
        this.bgColor = Color.BLACK;
        this.textSize = 90;
    }

    public void draw(Game game, int x, int y) {
        // Draw message at given position
        if (x + symbols.length > SnakeGame.WIDTH) {
            color = Color.RED;
            x = 0;
            symbols = specialSplit(Constants.TEXT_IS_OUT_OF_BOUNDS);
        }

        for (int i = 0; i < symbols.length; i++) {
            game.setCellValueEx((i + x), y, bgColor, symbols[i], color, textSize);
        }
    }

    void draw(Game game, int y) {
        // Draw message in the middle at given height;
        if (symbols.length > SnakeGame.WIDTH) {
            color = Color.RED;
            symbols = specialSplit(Constants.TEXT_IS_OUT_OF_BOUNDS);
        }

        int padding = symbols.length / 2;
        for (int i = 0; i < symbols.length; i++) {
            game.setCellValueEx((SnakeGame.WIDTH / 2 + i - padding), y, bgColor, symbols[i], color, textSize);
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

    private static class Constants {
        static final String TEXT_IS_OUT_OF_BOUNDS = "text is too long!";
    }
}
