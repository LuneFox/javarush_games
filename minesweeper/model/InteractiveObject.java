package com.javarush.games.minesweeper.model;

import com.javarush.games.minesweeper.Text;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.controller.Click;
import com.javarush.games.minesweeper.view.View;

/** Базовый интерактивный объект с координатами и размером. */
public abstract class InteractiveObject implements Drawable, Clickable {
    /** Ссылка на игру. */
    protected static MinesweeperGame game;
    /** Координата X. */
    public int x;
    /** Координата Y. */
    public int y;
    /** Ширина. */
    public int width;
    /** Высота. */
    public int height;
    /** X последнего клика. */
    protected int latestClickX;
    /** Y последнего клика. */
    protected int latestClickY;

    /** Создаёт экземпляр InteractiveObject. */
    public InteractiveObject() {
        this(0, 0);
    }

    /** Создаёт экземпляр InteractiveObject. */
    public InteractiveObject(int x, int y) {
        setPosition(x, y);
    }

    /** Задаёт позицию. */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Обрабатывает клик. */
    public void click(int x, int y, Click click) {
        rememberLatestClick(x, y);
        fireAction(click);
    }

    /** Запоминает координаты последнего клика. */
    private void rememberLatestClick(int x, int y) {
        latestClickX = x;
        latestClickY = y;
    }

    /** Выполняет действие клика. */
    private void fireAction(Click click) {
        if (click == Click.LEFT) {
            onLeftClick();
        } else if (click == Click.RIGHT) {
            onRightClick();
        } else {
            throw new IllegalArgumentException(Text.ERROR_INVALID_CLICK.getText());
        }
    }

    /** Обрабатывает левый клик. */
    public void onLeftClick() {
    }

    /** Обрабатывает правый клик. */
    public void onRightClick() {
    }

    /** Рисует объект на экране. */
    public abstract void draw();

    /** Проверяет, попадает ли точка в объект. */
    public boolean covers(int x, int y) {
        return (x >= this.x) && (x <= this.x + this.width) && (y >= this.y) && (y <= this.y + height);
    }

    /** Привязывает объект к экрану. */
    @SuppressWarnings("unchecked")
    public <T extends InteractiveObject> T linkView(View view) {
        view.linkObject(this);
        return (T) this;
    }

    /** Задаёт игру. */
    public static void setGame(MinesweeperGame game) {
        InteractiveObject.game = game;
    }
}
