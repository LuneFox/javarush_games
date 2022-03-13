package com.javarush.games.minesweeper.gui.image;

import com.javarush.engine.cell.*;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.Cache;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.view.View;

import java.util.HashSet;
import java.util.Set;

/**
 * An image that is drawn using colors and the number matrix. Can be mirrored, recolored etc.
 */

public class Image extends InteractiveObject {
    public static Set<Image> allImages = new HashSet<>(); // All images, including non-cached

    public static final Cache<ImageType, Image> cache = new Cache<ImageType, Image>(ImageType.values().length) {
        @Override
        protected Image put(ImageType type) {
            Image result = new Image(type);
            cache.put(type, result);
            return result;
        }
    };

    public static final int CENTER = Integer.MIN_VALUE;

    protected MinesweeperGame game = MinesweeperGame.getInstance();
    public int[][] matrix;  // matrix of color numbers
    public Color[] colors;  // an array to match colors and numbers
    private final ImageType imageType;
    private boolean updateColorRestricted;

    public enum Mirror {
        HORIZONTAL, VERTICAL, NONE
    }

    public Image(ImageType imageType) { // constructor without setting position (for loading images in memory)
        this(imageType, 0, 0);
    }

    public Image(ImageType imageType, View view) {
        this(imageType, 0, 0);
        linkView(view);
    }

    public Image(ImageType imageType, int x, int y) { // constructor with setting position at once
        super(x, y);
        this.imageType = imageType;
        this.updateColorRestricted = false;
        this.matrix = getMatrixFromStorage(imageType);
        this.height = matrix.length;
        this.width = matrix[0].length;
        allImages.add(this);
    }

    public void draw(Mirror mirror) {
        for (int innerY = 0; innerY < matrix.length; innerY++) {
            for (int innerX = 0; innerX < matrix[0].length; innerX++) {
                int pixel = matrix[innerY][innerX];
                boolean colorIsTransparent = (pixel == 0 || colors[pixel] == Color.NONE);
                if (colorIsTransparent) continue;

                if (mirror == Mirror.HORIZONTAL) {
                    game.display.setCellColor(
                            x + (matrix[0].length - 1 - innerX),
                            y + innerY,
                            colors[matrix[innerY][innerX]]
                    );
                } else if (mirror == Mirror.VERTICAL) {
                    game.display.setCellColor(
                            x + innerX,
                            y + (matrix.length - 1 - innerY),
                            colors[matrix[innerY][innerX]]
                    );
                } else {
                    game.display.setCellColor(
                            x + innerX,
                            y + innerY,
                            colors[matrix[innerY][innerX]]
                    );
                }
            }
        }
    }

    public final void draw(int x, int y, Mirror mirror) {
        setPosition(x, y);
        draw(mirror);
    }

    public final void draw(int x, int y) {
        draw(x, y, Mirror.NONE);
    }

    public void draw() {
        draw(Mirror.NONE);
    }

    public final void setPosition(int drawX, int drawY) { // negative value = middle
        this.x = (drawX == CENTER) ? (50 - matrix[0].length / 2) : drawX;
        this.y = (drawY == CENTER) ? (50 - matrix.length / 2) : drawY;
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

    public static void refreshAllImagesColors() {
        allImages.forEach(image -> {
            if (image.updateColorRestricted) return;
            image.colors = new ImageStorage(image.imageType).getColors();
        });
    }

    public void restrictColorUpdate(boolean updateColorRestricted) {
        this.updateColorRestricted = updateColorRestricted;
    }
}
