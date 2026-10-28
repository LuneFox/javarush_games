package com.javarush.games.spaceinvaders.model.gameobjects;

import com.javarush.games.spaceinvaders.Drawable;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Mirror;

/** Базовый объект поля: положение, спрайт и столкновения. */
public class GameObject implements Drawable {
    protected static SpaceInvadersGame game;

    private Sprite sprite;
    private double x;
    private double y;
    private boolean alive = true;
    private int lastCollisionX;
    private int lastCollisionY;

    /** Запоминает игру для всех объектов. */
    public static void setGame(SpaceInvadersGame game) {
        GameObject.game = game;
    }

    /** Создаёт объект в начале координат. */
    public GameObject() {
        this(0, 0);
    }

    /** Создаёт объект в заданной точке. */
    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /*
     * Положение и жизнь
     */

    /** Возвращает координату X. */
    public double getX() {
        return x;
    }

    /** Возвращает координату Y. */
    public double getY() {
        return y;
    }

    /** Переносит объект в заданную точку. */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** Сообщает, жив ли объект. */
    public boolean isAlive() {
        return alive;
    }

    /** Помечает объект уничтоженным. */
    public void destroy() {
        alive = false;
    }

    /** Сообщает, можно ли убрать объект с поля. */
    public boolean canBeRemoved() {
        return !alive && sprite.isAnimationFinished();
    }

    /*
     * Графика
     */

    /** Задаёт неподвижный спрайт. */
    public void setStaticView(int[][] frame) {
        sprite = new Sprite(game);
        sprite.setStaticView(frame);
    }

    /** Задаёт анимированный спрайт. */
    public void setAnimatedView(Sprite.Loop loop, int frameDelay, int[][]... frames) {
        sprite = new Sprite(game);
        sprite.setAnimatedView(loop, frameDelay, frames);
    }

    /** Рисует объект. */
    @Override
    public void draw() {
        sprite.draw(x, y, Mirror.NONE);
    }

    /** Рисует объект с отражением. */
    public void draw(Mirror mirror) {
        sprite.draw(x, y, mirror);
    }

    /** Стирает пиксель в точке последнего столкновения. */
    public void eraseCollisionPixel() {
        sprite.erasePixel(lastCollisionX, lastCollisionY);
    }

    /** Возвращает ширину спрайта. */
    public int getWidth() {
        return sprite.getWidth();
    }

    /** Возвращает высоту спрайта. */
    public int getHeight() {
        return sprite.getHeight();
    }

    /*
     * Столкновения
     */

    /** Определяет форму пересечения с другим объектом. */
    public Overlap overlapWith(GameObject other) {
        if (!collidesWith(other, Mirror.NONE)) {
            return Overlap.NONE;
        }

        double horizontalOverlap = Math.max(0, Math.min(
                x + getWidth(), other.x + other.getWidth()) - Math.max(x, other.x));
        double verticalOverlap = Math.max(0, Math.min(
                y + getHeight(), other.y + other.getHeight()) - Math.max(y, other.y));

        if (horizontalOverlap * verticalOverlap == 0) {
            return Overlap.NONE;
        }

        // Почти равные стороны означают удар углом
        final double threshold = 0.5;
        if (Math.abs(verticalOverlap - horizontalOverlap) < threshold) {
            return Overlap.SQUARE;
        }
        return (verticalOverlap > horizontalOverlap) ? Overlap.VERTICAL : Overlap.HORIZONTAL;
    }

    /** Проверяет попиксельное столкновение с другим объектом. */
    public boolean collidesWith(GameObject other, Mirror mirror) {
        for (int otherX = 0; otherX < other.getWidth(); otherX++) {
            for (int otherY = 0; otherY < other.getHeight(); otherY++) {
                if (!other.sprite.isPixelVisible(otherX, otherY)) {
                    continue;
                }

                double mirroredX = (mirror == Mirror.HORIZONTAL) ? (other.sprite.getWidth() - otherX) : otherX;
                if (collidesWithPoint(other.x + mirroredX, other.y + otherY)) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Проверяет, попадает ли точка в непрозрачный пиксель. */
    private boolean collidesWithPoint(double pointX, double pointY) {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                boolean sameCell = (int) (x + this.x) == (int) pointX && (int) (y + this.y) == (int) pointY;
                if (sameCell && sprite.isPixelVisible(x, y)) {
                    rememberCollisionPoint(x, y);
                    return true;
                }
            }
        }
        return false;
    }

    /** Запоминает точку последнего столкновения. */
    private void rememberCollisionPoint(int x, int y) {
        lastCollisionX = x;
        lastCollisionY = y;
    }
}
