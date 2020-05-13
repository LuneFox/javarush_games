package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class Message {
    private String[] splitText;
    private Color color;
    private Color bgColor = Color.BLACK;
    private int textSize = 70;

    // create message object with given text
    Message(String text, Color color) {
        splitText = specialSplit(text);
        this.color = color;
    }

    // draw message at given position
    public void draw(Game game, int x, int y) {
        if (x + splitText.length > SnakeGame.WIDTH) {
            color = Color.RED;
            x = 0;
            splitText = specialSplit("text is out of bounds!");
        }

        for (int i = 0; i < splitText.length; i++) {
            game.setCellValueEx((i + x), y, bgColor, splitText[i], color, textSize);
        }
    }

    // draw message in the middle
    void draw(Game game) {
        if (splitText.length > 32) {
            color = Color.RED;
            splitText = specialSplit("text is to long!");
        }

        int padding = splitText.length / 2;
        for (int i = 0; i < splitText.length; i++) {
            game.setCellValueEx((SnakeGame.WIDTH / 2 + i - padding), 1, bgColor, splitText[i], color, textSize);
        }


    }

    // draw message in the middle at given height;
    void draw(Game game, int y) {
        if (splitText.length > 32) {
            color = Color.RED;
            splitText = specialSplit("text is to long!");
        }

        int padding = splitText.length / 2;
        for (int i = 0; i < splitText.length; i++) {
            game.setCellValueEx((SnakeGame.WIDTH / 2 + i - padding), y, bgColor, splitText[i], color, textSize);
        }


    }

    private String[] specialSplit(String text) {
        //if (text.length() % 2 == 1) text = text + " ";
        //return text.toUpperCase().split("(?<=\\G..)");
        return text.toUpperCase().split("");
    }
}
