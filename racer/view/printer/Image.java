package com.javarush.games.racer.view.printer;

import com.javarush.engine.cell.Color;
import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.model.Drawable;
import com.javarush.games.racer.view.Display;

public class Image implements Drawable {
    public static final Cache<ImageType, Image> IMAGE_CACHE;
    public static final int CENTER = Integer.MIN_VALUE;
    protected static RacerGame game;

    public int x;
    public int y;
    public int width;
    public int height;
    public int[][] matrix;
    public Color[] colors;

    public static void setGame(RacerGame game) {
        Image.game = game;
    }

    public enum Mirror {
        HORIZONTAL, VERTICAL, NONE
    }

    static {
        IMAGE_CACHE = new Cache<ImageType, Image>(ImageType.values().length) {
            @Override
            protected Image put(ImageType type) {
                Image result = new Image(type);
                cache.put(type, result);
                return result;
            }
        };
    }

    public Image(ImageType type) {
        this(type, 0, 0);
    }

    public Image(ImageType type, int x, int y) {
        setPosition(x, y);
        this.matrix = getMatrixFromStorage(type);
        this.height = matrix.length;
        this.width = matrix[0].length;
    }

    public final void setPosition(int drawX, int drawY) {
        this.x = drawX == CENTER ? getCenterH() : drawX;
        this.y = drawY == CENTER ? getCenterV() : drawY;
    }

    private int getCenterH() {
        return (Display.SIZE / 2) - (matrix[0].length / 2);
    }

    private int getCenterV() {
        return (Display.SIZE / 2) - (matrix.length / 2);
    }

    public int[][] getMatrixFromStorage(ImageType imageType) {
        ImageStorage storage = new ImageStorage(imageType);
        colors = storage.getColors();
        return storage.getData();
    }

    public void draw(Mirror mirror) {
        for (int innerY = 0; innerY < height; innerY++) {
            for (int innerX = 0; innerX < width; innerX++) {
                int pixel = matrix[innerY][innerX];
                if (isPixelTransparent(pixel)) continue;
                int drawX = x + ((mirror == Mirror.HORIZONTAL) ? (width - 1 - innerX) : innerX);
                int drawY = y + ((mirror == Mirror.VERTICAL) ? (height - 1 - innerY) : innerY);
                game.display.drawPixel(drawX, drawY, colors[pixel]);
            }
        }
    }

    public final void draw(int x, int y) {
        setPosition(x, y);
        draw(Mirror.NONE);
    }

    public void draw() {
        draw(Mirror.NONE);
    }

    private boolean isPixelTransparent(int pixel) {
        final int TRANSPARENT = 0;
        return (pixel == TRANSPARENT || colors[pixel] == Color.NONE);
    }

    public final void changeColor(Color color, int number) {
        try {
            this.colors[number] = color;
        } catch (IndexOutOfBoundsException ignored) {
        }
    }
}
