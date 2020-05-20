package com.javarush.games.moonlander;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.games.snake.SnakeGame;

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
        if (x + splitText.length > MoonLanderGame.WIDTH) {
            color = Color.RED;
            x = 0;
            splitText = specialSplit("text is out of bounds!");
        }

        for (int i = 0; i < splitText.length; i++) {
            game.setCellValueEx((i + x), y, bgColor, splitText[i], color, textSize);
        }
    }

    void draw(Game game, int y) {
        // Draw message in the middle at given height;
        if (splitText.length > MoonLanderGame.WIDTH) {
            color = Color.RED;
            splitText = specialSplit("text is to long!");
        }

        int padding = splitText.length / 2;
        for (int i = 0; i < splitText.length; i++) { // JavaRush browser fix
            game.setCellValueEx((MoonLanderGame.WIDTH / 2 + i - padding), y, bgColor, splitText[i], color, textSize);
        }


    }


    // MECHANICS

    private String[] specialSplit(String text) {
        String[] result = text.toUpperCase().split("");
        //if (text.length() % 2 == 1) text = text + " ";
        //return text.toUpperCase().split("(?<=\\G..)");
        if (!result[0].equals("")) {
            return result;
        } else {
            String[] resultJavaRush = new String[result.length - 1];
            for (int i = 0; i < resultJavaRush.length; i++) {
                resultJavaRush[i] = result[i + 1];
            }
            return resultJavaRush;
        }
    }
}
