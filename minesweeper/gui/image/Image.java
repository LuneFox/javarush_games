package com.javarush.games.minesweeper.gui.image;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Cache;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.view.View;

import java.util.HashSet;
import java.util.Set;

/**
 * An image that is drawn using colors and the number matrix. Can be mirrored, recolored etc.
 */

public class Image extends InteractiveObject {
    public static final Cache<ImageType, Image> cache;
    public static final int CENTER = Integer.MIN_VALUE;
    public static final Set<Image> allImages = new HashSet<>(); // All images, including non-cached

    private final ImageType type;
    public int[][] matrix;  // matrix of color numbers
    public Color[] colors;  // an array to match colors and numbers
    private boolean updatingColorRestricted;

    public enum Mirror {
        HORIZONTAL, VERTICAL, NONE
    }

    static {
        cache = new Cache<ImageType, Image>(ImageType.values().length) {
            @Override
            protected Image put(ImageType type) {
                Image result = new Image(type);
                cache.put(type, result);
                return result;
            }
        };
    }

    // Need this to fully change color theme
    public static void updateColors() {
        for (Image image : allImages) {
            if (image.updatingColorRestricted) continue;
            ImageStorage storage = new ImageStorage(image.type);
            image.colors = storage.getColors();
        }
    }

    // Main constructor
    public Image(ImageType type, int x, int y) { // constructor with setting position at once
        super(x, y);
        this.type = type;
        this.matrix = getMatrixFromStorage(type);
        this.height = matrix.length;
        this.width = matrix[0].length;
        Image.allImages.add(this);
    }

    // Constructor with linking to view
    public Image(ImageType type, View view) {
        this(type, 0, 0);
        linkView(view);
    }

    // Constructor without setting position (for loading images into memory)
    public Image(ImageType type) {
        this(type, 0, 0);
    }

    // Main draw method
    public void draw(Mirror mirror) {
        for (int innerY = 0; innerY < matrix.length; innerY++) {
            for (int innerX = 0; innerX < matrix[0].length; innerX++) {
                int pixel = matrix[innerY][innerX];
                if (isTransparent(pixel)) continue;

                int drawX = x + ((mirror == Mirror.HORIZONTAL) ? (matrix[0].length - 1 - innerX) : innerX);
                int drawY = y + ((mirror == Mirror.VERTICAL) ? (matrix.length - 1 - innerY) : innerY);

                game.setDisplayPixel(drawX, drawY, colors[pixel]);
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

    // 0 always stands for transparent
    private boolean isTransparent(int pixel) {
        return (pixel == 0 || colors[pixel] == Color.NONE);
    }

    public final void setPosition(int drawX, int drawY) { // negative value = middle
        this.x = drawX == CENTER ? 50 - matrix[0].length / 2 : drawX;
        this.y = drawY == CENTER ? 50 - matrix.length / 2 : drawY;
    }

    public final void replaceColor(Color color, int number) {
        try {
            this.colors[number] = color;
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    public int[][] getMatrixFromStorage(ImageType imageType) {
        ImageStorage storage = new ImageStorage(imageType);
        colors = storage.getColors();
        return storage.getData();
    }

    public void restrictColorChange(boolean updateColorRestricted) {
        this.updatingColorRestricted = updateColorRestricted;
    }
}
