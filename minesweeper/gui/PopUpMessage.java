package com.javarush.games.minesweeper.gui;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.Options;

/**
 * Shows short message over the screen. Singleton.
 */

public class PopUpMessage extends InteractiveObject {
    private static final int TIME_TO_LIVE = 60;
    private static final int SLIDE_STEP = 1;
    private static final PopUpMessage INSTANCE = new PopUpMessage();
    private final Image panel;
    private String text;
    private int posY;
    private int timeLeft;
    private boolean slideFromBottom;

    private PopUpMessage() {
        this.panel = Image.cache.get(ImageType.GUI_POPUP_MESSAGE);
        height = panel.height;
        width = panel.width;
        panel.setPosition(Image.CENTER, -height);
    }

    public static void drawInstance() {
        if (INSTANCE.isDead()) return;
        INSTANCE.draw();
        INSTANCE.reduceTimeLeft();
    }

    public static void show(String text) {
        setInstanceText(text);
        selectInstanceInitialPosition();
        resurrectInstance();
    }

    private static void setInstanceText(String text) {
        INSTANCE.text = text;
    }

    private static void selectInstanceInitialPosition() {
        INSTANCE.slideFromBottom = Controller.clickedOnUpperHalf();
        INSTANCE.posY = INSTANCE.slideFromBottom ? Display.SIZE : -INSTANCE.height;
    }

    private static void resurrectInstance() {
        INSTANCE.timeLeft = TIME_TO_LIVE;
    }

    @Override
    public void draw() {
        if (!Options.displayMessageSelector.isEnabled()) return;
        slide();
        drawPanel();
        printMessage();
    }

    private void slide() {
        if (isTimeToSlideIn()) {
            slideIn();
        } else if (isTimeToSlideOut()) {
            slideOut();
        }
    }

    private boolean isTimeToSlideIn() {
        return (TIME_TO_LIVE - timeLeft) < height;
    }

    private boolean isTimeToSlideOut() {
        return timeLeft < height;
    }

    private void slideIn() {
        posY += slideFromBottom ? -SLIDE_STEP : SLIDE_STEP;
    }

    private void slideOut() {
        posY += slideFromBottom ? SLIDE_STEP : -SLIDE_STEP;
    }

    private void drawPanel() {
        panel.draw(Image.CENTER, posY);
    }

    private void printMessage() {
        Printer.print(text, Color.WHITE, Printer.CENTER, posY + 1);
    }

    private boolean isDead() {
        return timeLeft <= 0;
    }

    private void reduceTimeLeft() {
        timeLeft--;
    }
}
