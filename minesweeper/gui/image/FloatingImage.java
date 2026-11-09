package com.javarush.games.minesweeper.gui.image;

import com.javarush.games.minesweeper.view.View;

/** Изображение с плавным вертикальным покачиванием. */
public class FloatingImage extends Image {
    /** Шаг анимации. */
    private static final float STEP = 0.2f;
    /** Текущий сдвиг. */
    private float shift;
    /** Направление покачивания. */
    private boolean directionDown;

    /** Создаёт экземпляр FloatingImage. */
    public FloatingImage(ImageType imageType, View view) {
        super(imageType, view);
    }

    /** Рисует объект на экране. */
    public void draw(double maxDistance, int x, int y) {
        this.setPosition(x, y);
        shiftVertically(maxDistance, y);
        super.draw();
    }

    /** Сдвигает изображение по вертикали. */
    private void shiftVertically(double maxDistance, int y) {
        shift += (directionDown ? STEP : -STEP);

        if (Math.abs(shift) > maxDistance) {
            changeDirection();
        }

        this.y = (int) (y + shift);
    }

    /** Меняет направление покачивания. */
    private void changeDirection() {
        directionDown = !directionDown;
    }
}
