package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class Message {
    private String[] splitText;
    private Color color;
    private Color bgColor = Color.BLACK;
    private int textSize = 90;


    // CONSTRUCTOR

    Message(String text, Color color) {
        // Create message object with given text and color
        splitText = specialSplit(text);
        this.color = color;
    }


    // VISUALS

    public void draw(Game game, int x, int y) {
        // Draw message at given position
        if (x + splitText.length > SnakeGame.WIDTH) {
            color = Color.RED;
            x = 0;
            splitText = specialSplit("text is out of bounds!");
        }

        for (int i = 0; i < splitText.length; i++) {
            if (i == 0 && splitText[i].equals("")) { // JavaRush browser fix
                i--;
                continue;
            }
            game.setCellValueEx((i + x), y, bgColor, splitText[i], color, textSize);
        }
    }

    void draw(Game game, int y) {
        // Draw message in the middle at given height;
        if (splitText.length > SnakeGame.WIDTH) {
            color = Color.RED;
            splitText = specialSplit("text is to long!");
        }

        int padding = splitText.length / 2;
        for (int i = 0; i < splitText.length; i++) { // JavaRush browser fix
            if (i == 0 && splitText[i].equals("")) {
                i--;
                continue;
            }
            game.setCellValueEx((SnakeGame.WIDTH / 2 + i - padding), y, bgColor, splitText[i], color, textSize);
        }


    }


    // MECHANICS

    private String[] specialSplit(String text) {
        //if (text.length() % 2 == 1) text = text + " ";
        //return text.toUpperCase().split("(?<=\\G..)");
        return text.toUpperCase().split("");
    }
}
