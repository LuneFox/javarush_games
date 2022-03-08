package com.javarush.games.minesweeper.model;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.model.options.Options;
import com.javarush.games.minesweeper.view.graphics.*;

/**
 * Shows short message over the screen. Singleton.
 */

public class Message extends DrawableObject {
    private static final Message INSTANCE = new Message();
    private final Image background;
    private String text;
    private int timeToLive;      // current time
    private int timeToLiveStart; // original time
    private int yPos;            // overall draw height

    private Message() {
        this.background = Cache.get(VisualElement.WIN_MESSAGE);
        background.setPosition(5, -11);
    }

    @Override
    public void draw() {
        // Slide animation
        if (timeToLiveStart - timeToLive < 10) {
            yPos += 2;
        } else if (timeToLive < 10) {
            yPos -= 2;
        }
        background.draw(5, yPos);
        Printer.print(text, Color.WHITE, Printer.CENTER, yPos + 1);
    }

    public static void drawMessage() {
        if (INSTANCE.timeToLive <= 0) {
            return;
        }
        if (Options.displayMessageSelector.isEnabled()) {
            game.display.setInterlaceEnabled(false);
            INSTANCE.draw();
        }
        INSTANCE.timeToLive--;
    }

    public static void show(String text) {
        INSTANCE.timeToLive = 60;
        INSTANCE.timeToLiveStart = 60;
        INSTANCE.text = text;
        INSTANCE.yPos = -11;
    }
}
