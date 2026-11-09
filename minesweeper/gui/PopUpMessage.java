package com.javarush.games.minesweeper.gui;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.controller.Controller;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.Options;

/** Всплывающее сообщение поверх экрана. */
public class PopUpMessage extends InteractiveObject {
    /** Время жизни. */
    private static final int TIME_TO_LIVE = 60;
    /** Шаг слайда. */
    private static final int SLIDE_STEP = 1;
    /** Единственный экземпляр сообщения. */
    private static final PopUpMessage INSTANCE = new PopUpMessage();
    /** Панель. */
    private final Image panel;
    /** Текст. */
    private String text;
    /** Позиция по Y. */
    private int posY;
    /** Оставшееся время. */
    private int timeLeft;
    /** Слайд снизу. */
    private boolean slideFromBottom;

    /** Создаёт экземпляр PopUpMessage. */
    private PopUpMessage() {
        this.panel = Image.cache.get(ImageType.GUI_POPUP_MESSAGE);
        height = panel.height;
        width = panel.width;
        panel.setPosition(Image.CENTER, -height);
    }

    /** Рисует текущее сообщение. */
    public static void drawInstance() {
        if (INSTANCE.isDead()) return;
        INSTANCE.draw();
        INSTANCE.reduceTimeLeft();
    }

    /** Показывает сообщение. */
    public static void show(String text) {
        setInstanceText(text);
        selectInstanceInitialPosition();
        resurrectInstance();
    }

    /** Задаёт текст сообщения. */
    private static void setInstanceText(String text) {
        INSTANCE.text = text;
    }

    /** Выбирает начальную позицию сообщения. */
    private static void selectInstanceInitialPosition() {
        INSTANCE.slideFromBottom = Controller.clickedOnUpperHalf();
        INSTANCE.posY = INSTANCE.slideFromBottom ? Display.SIZE : -INSTANCE.height;
    }

    /** Перезапускает показ сообщения. */
    private static void resurrectInstance() {
        INSTANCE.timeLeft = TIME_TO_LIVE;
    }

    /** Рисует объект на экране. */
    @Override
    public void draw() {
        if (!Options.getDisplayMessageSelector().isEnabled()) return;
        slide();
        drawPanel();
        printMessage();
    }

    /** Двигает сообщение. */
    private void slide() {
        if (isTimeToSlideIn()) {
            slideIn();
        } else if (isTimeToSlideOut()) {
            slideOut();
        }
    }

    /** Сообщает, пора ли вводить сообщение. */
    private boolean isTimeToSlideIn() {
        return (TIME_TO_LIVE - timeLeft) < height;
    }

    /** Сообщает, пора ли уводить сообщение. */
    private boolean isTimeToSlideOut() {
        return timeLeft < height;
    }

    /** Вводит сообщение. */
    private void slideIn() {
        posY += slideFromBottom ? -SLIDE_STEP : SLIDE_STEP;
    }

    /** Уводит сообщение. */
    private void slideOut() {
        posY += slideFromBottom ? SLIDE_STEP : -SLIDE_STEP;
    }

    /** Рисует панель. */
    private void drawPanel() {
        panel.draw(Image.CENTER, posY);
    }

    /** Печатает сообщение. */
    private void printMessage() {
        Printer.print(text, Color.WHITE, Printer.CENTER, posY + 1);
    }

    /** Сообщает, закончилось ли время сообщения. */
    private boolean isDead() {
        return timeLeft <= 0;
    }

    /** Уменьшает оставшееся время сообщения. */
    private void reduceTimeLeft() {
        timeLeft--;
    }
}
