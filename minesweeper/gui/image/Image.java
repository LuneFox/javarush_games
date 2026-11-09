package com.javarush.games.minesweeper.gui.image;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Cache;
import com.javarush.games.minesweeper.gui.Display;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.view.View;

/** Пиксельное изображение с матрицей и палитрой. */
public class Image extends InteractiveObject {
    /** Кэш значений. */
    public static final Cache<ImageType, Image> cache;
    /** Маркер центрирования. */
    public static final int CENTER = Integer.MIN_VALUE;

    /** Тип изображения. */
    private final ImageType type;
    /** Матрица пикселей изображения. */
    private int[][] matrix;
    /** Палитра изображения. */
    private Color[] colors;
    /** Запрещена ли перекраска. */
    private boolean isColorLocked;

    static {
        cache = new Cache<ImageType, Image>(ImageType.values().length) {
            /** Создаёт и сохраняет значение. */
            @Override
            protected Image put(ImageType type) {
                Image result = new Image(type);
                cache.put(type, result);
                return result;
            }
        };
    }

    /** Создаёт экземпляр Image. */
    public Image(ImageType type) {
        this(type, 0, 0);
    }

    /** Создаёт экземпляр Image. */
    public Image(ImageType type, View view) {
        this(type);
        linkView(view);
    }

    /** Создаёт экземпляр Image. */
    public Image(ImageType type, int x, int y) {
        this(type, x, y, true);
    }

    /**
     * Создаёт изображение; при {@code track == false} оно не попадает в общий реестр
     * перекрашивания, чтобы временные спрайты клеток не удерживались в памяти.
     */
    public Image(ImageType type, int x, int y, boolean track) {
        super(x, y);
        this.type = type;
        this.matrix = getMatrixFromStorage(type);
        this.height = matrix.length;
        this.width = matrix[0].length;
        if (track) ImageManager.addImage(this);
    }

    /** Задаёт позицию. */
    public final void setPosition(int drawX, int drawY) {
        this.x = drawX == CENTER ? getCenterH() : drawX;
        this.y = drawY == CENTER ? getCenterV() : drawY;
    }

    /** Вычисляет горизонтальный центр. */
    private int getCenterH() {
        return (Display.SIZE / 2) - (matrix[0].length / 2);
    }

    /** Вычисляет вертикальный центр. */
    private int getCenterV() {
        return (Display.SIZE / 2) - (matrix.length / 2);
    }

    /** Возвращает матрицу из хранилища. */
    public int[][] getMatrixFromStorage(ImageType imageType) {
        ImageStorage storage = new ImageStorage(imageType);
        colors = storage.getColors();
        return storage.getData();
    }

    /** Рисует объект на экране. */
    public void draw(Mirror mirror) {
        for (int innerY = 0; innerY < height; innerY++) {
            for (int innerX = 0; innerX < width; innerX++) {
                int pixel = matrix[innerY][innerX];
                if (isPixelTransparent(pixel)) continue;
                int drawX = x + ((mirror == Mirror.HORIZONTAL) ? (width - 1 - innerX) : innerX);
                int drawY = y + ((mirror == Mirror.VERTICAL) ? (height - 1 - innerY) : innerY);
                game.drawPixel(drawX, drawY, colors[pixel]);
            }
        }
    }

    /** Рисует объект на экране. */
    public final void draw(int x, int y) {
        setPosition(x, y);
        draw(Mirror.NONE);
    }

    /** Рисует объект на экране. */
    public void draw() {
        draw(Mirror.NONE);
    }

    /** Сообщает, прозрачен ли пиксель. */
    private boolean isPixelTransparent(int pixel) {
        final int TRANSPARENT = 0;
        return (pixel == TRANSPARENT || colors[pixel] == Color.NONE);
    }

    /** Меняет цвет палитры. */
    public final void changeColor(Color color, int number) {
        try {
            this.colors[number] = color;
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    /** Запрещает автоматическую перекраску. */
    public void lockColors() {
        isColorLocked = true;
    }

    // Геттеры, setters

    /** Возвращает тип. */
    public ImageType getType() {
        return type;
    }

    /** Сообщает, запрещена ли перекраска. */
    public boolean isColorLocked() {
        return isColorLocked;
    }

    /** Задаёт палитру изображения. */
    public void setColors(Color[] colors) {
        this.colors = colors;
    }

    /** Возвращает матрицу. */
    public int[][] getMatrix() {
        return matrix;
    }

    /** Задаёт матрицу изображения. */
    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
}
