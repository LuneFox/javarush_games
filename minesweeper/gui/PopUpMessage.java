package com.javarush.games.minesweeper.gui;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.DrawableObject;

/**
 * Shows short message over the screen. Singleton.
 */

public class PopUpMessage extends DrawableObject {
    private static final int STEP = 1;
    private static final PopUpMessage INSTANCE = new PopUpMessage();
    private final Image background;
    private String text;
    private int timeToLive;             // current time
    private int timeToLiveStart;        // original time
    private int yPos;                   // overall draw height
    private boolean slideFromBottom;

    private PopUpMessage() {
        this.background = Image.cache.get(ImageType.WIN_MESSAGE);
        height = 11;
        width = background.width;
        background.setPosition(Image.CENTER, -height);
        slideFromBottom = false;
    }

    @Override
    public void draw() {
        // Slide animation
        if (timeToLiveStart - timeToLive < height) {
            yPos += slideFromBottom ? -STEP : STEP;
        } else if (timeToLive < height) {
            yPos += slideFromBottom ? STEP : -STEP;
        }
        background.draw(Image.CENTER, yPos);
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
        INSTANCE.slideFromBottom = Controller.clickedOnUpperHalf();
        INSTANCE.yPos = INSTANCE.slideFromBottom ? 100 : -INSTANCE.height;
    }

    public static int getTimeToLive() {
        return INSTANCE.timeToLive;
    }
}
