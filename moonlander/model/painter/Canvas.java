package com.javarush.games.moonlander.model.painter;

import com.javarush.engine.cell.Color;
import com.javarush.games.moonlander.MoonLanderGame;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Canvas extends DrawableObject {
    private static final MoonLanderGame game = MoonLanderGame.getInstance();
    private static final int DEFAULT_EDIT_AREA_SIZE = 16;

    public int editAreaX;
    public int editAreaY;
    private int[][] colorMatrix;
    private List<Color> backgroundColors;

    public Canvas(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.colorMatrix = new int[height][width];
        this.editAreaX = DEFAULT_EDIT_AREA_SIZE;
        this.editAreaY = DEFAULT_EDIT_AREA_SIZE;
        backgroundColors = new LinkedList<>(Arrays.asList(
                Color.GREY,
                Color.DARKGREY,
                Color.SILVER,
                Color.LIGHTGREY,
                Color.WHITE,
                Color.BLACK,
                Color.DARKSLATEGREY
        ));
    }

    @Override
    public void draw() {
        drawBackground();
        drawColorMatrix();
        drawMask();
        drawMatrixSizeMarkers();
        drawClearCanvasButton();
    }

    private void drawColorMatrix() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int value = colorMatrix[y][x];
                game.setCellValueEx(x + posX, y + posY,
                        Color.values()[value],
                        (value == 0 || !game.painter.showNumbers) ? "" : String.valueOf(colorMatrix[y][x]),
                        ColorPalette.getOverlayNumberColor(value));
            }
        }
    }

    /**
     * Black mask that covers inactive part of canvas
     */
    private void drawMask() {
        int maskX = MoonLanderGame.WIDTH - posX - editAreaX;
        int maskY = MoonLanderGame.HEIGHT - posY - editAreaY;
        for (int x = posX; x < MoonLanderGame.WIDTH; x++) {
            for (int y = MoonLanderGame.HEIGHT - maskY; y < MoonLanderGame.HEIGHT; y++) {
                game.setCellValueEx(x, y, Color.BLACK, "", Color.NONE);
            }
        }
        for (int y = posY; y < MoonLanderGame.HEIGHT; y++) {
            for (int x = MoonLanderGame.WIDTH - maskX; x < MoonLanderGame.WIDTH; x++) {
                game.setCellValueEx(x, y, Color.BLACK, "", Color.NONE);
            }
        }
    }

    private void drawBackground() {
        for (int x = 0; x < posX + width; x++) {
            for (int y = 0; y < posY + height; y++) {
                if (x < posX || y < posY) {
                    game.setCellValueEx(x, y, Color.BLACK, "", Color.NONE);
                } else {
                    game.setCellValueEx(x, y, backgroundColors.get(0), "", Color.NONE);
                }
            }
        }
    }

    private void drawMatrixSizeMarkers() {
        game.setCellValueEx(posX - 1, posY + editAreaY - 1, Color.NONE, String.valueOf(editAreaY), Color.WHITE);
        game.setCellValueEx(editAreaX + posX - 1, posY - 1, Color.NONE, String.valueOf(editAreaX), Color.WHITE);
        game.setCellValueEx(posX - 1, posY - 1, Color.NONE, "0", Color.WHITE);

        for (int x = posX - 1; x < editAreaX; x++) {
            if (x % 10 == 0) {
                game.setCellValueEx(x + posX - 1, posY - 1, Color.NONE, String.valueOf(x), Color.WHITE);
            }
        }
        for (int y = posY - 1; y < editAreaY; y++) {
            if (y % 10 == 0) {
                game.setCellValueEx(posX - 1, y + posY - 1, Color.NONE, String.valueOf(y), Color.WHITE);
            }
        }
    }

    private void drawClearCanvasButton() {
        game.setCellValueEx(39, 6, Color.DARKRED, "Х", Color.WHITE, 90);
    }

    public void changeBackground() {
        Color movingColor = backgroundColors.get(0);
        backgroundColors.remove(movingColor);
        backgroundColors.add(movingColor);
        game.painter.undoManager.save();
    }

    public void resizeEditArea(int x, int y) {
        game.painter.undoManager.save();
        editAreaX += x;
        editAreaY += y;
        if (editAreaX < 1) editAreaX++;
        if (editAreaY < 1) editAreaY++;
        if (editAreaX > width) editAreaX--;
        if (editAreaY > height) editAreaY--;
    }

    public void rotateClockWise() {
        int[][] rotatedEditArea = new int[editAreaX][editAreaY];

        for (int y = 0; y < editAreaY; y++) {
            for (int x = 0; x < editAreaX; x++) {
                rotatedEditArea[x][editAreaY - 1 - y] = colorMatrix[y][x];
            }
        }

        // Swap edit area lengths
        editAreaX = editAreaX + editAreaY;
        editAreaY = editAreaX - editAreaY;
        editAreaX = editAreaX - editAreaY;

        colorMatrix = new int[height][width];

        for (int y = 0; y < editAreaY; y++) {
            for (int x = 0; x < editAreaX; x++) {
                colorMatrix[y][x] = rotatedEditArea[y][x];
            }
        }
    }

    public void rotateCounterClockWise() {
        for (int i = 0; i < 3; i++) {
            rotateClockWise();
        }
    }

    public void moveDown() {
        for (int y = editAreaY - 1; y > 0; y--) {
            for (int x = 0; x < editAreaX; x++) {
                colorMatrix[y][x] = colorMatrix[y - 1][x];
                if (y == 1) {
                    colorMatrix[y - 1][x] = 0;
                }
            }
        }
    }

    public void moveLeft() {
        rotateCounterClockWise();
        moveDown();
        rotateClockWise();
    }

    public void moveUp() {
        rotateClockWise();
        rotateClockWise();
        moveDown();
        rotateCounterClockWise();
        rotateCounterClockWise();
    }

    public void moveRight() {
        rotateClockWise();
        moveDown();
        rotateCounterClockWise();
    }

    public void flipHorizontal() {
        int[][] flippedEditArea = new int[editAreaY][editAreaX];

        for (int y = 0; y < editAreaY; y++) {
            for (int x = 0; x < editAreaX; x++) {
                flippedEditArea[y][editAreaX - x - 1] = colorMatrix[y][x];
            }
        }

        colorMatrix = new int[height][width];

        for (int y = 0; y < editAreaY; y++) {
            for (int x = 0; x < editAreaX; x++) {
                colorMatrix[y][x] = flippedEditArea[y][x];
            }
        }
    }

    public void flipVertical() {
        rotateClockWise();
        flipHorizontal();
        rotateCounterClockWise();
    }

    public void clear() {
        this.colorMatrix = new int[height][width];
        game.painter.undoManager.save();
    }

    public boolean checkClickOnEditArea(int x, int y) {
        return (x >= posX && x < posX + editAreaX) && (y >= posY && y < posY + editAreaY);
    }

    public int[][] getColorMatrix() {
        return colorMatrix;
    }

    public Color getBackgroundColor() {
        return backgroundColors.get(0);
    }

    public void export() {
        String consoleResult = Util.exportArrayToCode(colorMatrix, editAreaX, editAreaY, true);
        String browserResult = Util.exportArrayToCode(colorMatrix, editAreaX, editAreaY, false);
        // System.out.println(consoleResult);
        game.setCellValueEx(0, 0, Color.BLACK, browserResult, Color.BLACK, 1);
        Painter.showHelp("Массив цветов эскпортирован в код!\n\n" +
                "Кликни где-нибудь сбоку от игры в пустое место, нажми Ctrl+Shift+C (исследовать элемент)\n" +
                "и найди (Ctrl+F) элемент, содержащий \"nеw int[][]\", это и будет твой массив :)\n\n" +
                "Теперь можешь вставить рисунок в свою JavaRush игру!\n\n\n" +
                "Молодец, всё правильно! Вот твой массив, копируй:\n" +
                browserResult);
    }

    public Canvas createClone() {
        Canvas clone = new Canvas(this.posX, this.posY, this.width, this.height);
        clone.editAreaX = this.editAreaX;
        clone.editAreaY = this.editAreaY;
        clone.backgroundColors = this.backgroundColors;
        for (int y = 0; y < colorMatrix.length; y++) {
            System.arraycopy(this.colorMatrix[y], 0, clone.colorMatrix[y], 0, colorMatrix[0].length);
        }
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Canvas canvas = (Canvas) o;
        return editAreaX == canvas.editAreaX
                && editAreaY == canvas.editAreaY
                && Arrays.deepEquals(colorMatrix, canvas.colorMatrix)
                && Objects.equals(backgroundColors, canvas.backgroundColors);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(editAreaX, editAreaY, backgroundColors);
        result = 31 * result + Arrays.deepHashCode(colorMatrix);
        return result;
    }
}
