package com.javarush.games.ticktacktoe.model.gameobjects;

import com.javarush.games.ticktacktoe.view.Drawable;
import com.javarush.games.ticktacktoe.TicTacToeGame;

/**
 * Игровой объект
 *
 * @author LuneFox
 */
public class GameObject implements Drawable {
    /** Экземпляр игры */
    protected static TicTacToeGame game;
    /** Спрайт */
    private Sprite sprite;
    /** Расположение по горизонтали */
    public double x;
    /** Расположение по вертикали */
    public double y;

    /**
     * Привязка к экземпляру игры
     *
     * @param game экземпляр игры
     */
    public static void setGame(TicTacToeGame game) {
        GameObject.game = game;
    }

    public GameObject() {
        this(0, 0);
    }

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Установка неподвижного изображения
     *
     * @param frame изображение (один кадр)
     */
    public void setStaticView(int[][] frame) {
        sprite = new Sprite(game);
        sprite.setStaticView(frame);
    }

    /**
     * Установка анимированного изображения
     *
     * @param loop зацикливать анимацию
     * @param nextFrameDelay задержка перед отображением следующего кадра (скорость анимации)
     * @param frames ряд изображений (кадры)
     */
    public void setAnimatedView(Sprite.Loop loop, int nextFrameDelay, int[][]... frames) {
        sprite = new Sprite(game);
        sprite.setAnimatedView(loop, nextFrameDelay, frames);
    }

    /**
     * Отрисовка без отражения
     */
    public void draw() {
        sprite.draw(x, y, Mirror.NONE);
    }

    /**
     * Отрисовка с установкой отражения
     *
     * @param mirror тип отражения
     */
    public void draw(Mirror mirror) {
        sprite.draw(x, y, mirror);
    }

    /**
     * Получить визуальную ширину объекта
     *
     * @return ширина спрайта
     */
    public int getWidth() {
        return sprite.width;
    }

    /**
     * Получить визуальную высоту объекта
     *
     * @return высота спрайта
     */
    public int getHeight() {
        return sprite.height;
    }

    /**
     * Установить положение на экране
     * @param x по горизонтали
     * @param y по вертикали
     */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Получить положение по горизонтали на сетке поля 10х10
     *
     * @return координата X от 0 до 9
     */
    public int getBoardX() {
        return (int) ((x - 10) / 10);
    }

    /**
     * Получить положение по вертикали на сетке поля 10х10
     *
     * @return координата Y от 0 до 9
     */
    public int getBoardY() {
        return (int) ((y - 10) / 10);
    }
}