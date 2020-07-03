package com.javarush.games.moonlander;

import com.javarush.engine.cell.Color;

import java.util.ArrayList;

public class SpritePainterTool {
    private MoonLanderGame game;
    private ArrayList<Color> backgroundColors;
    private ArrayList<int[][]> savedStates;
    private int brush;
    private int spriteSizeX;
    private int spriteSizeY;
    private int[][] sprite;
    boolean fillSelected = false;
    boolean replaceSelected = false;

    SpritePainterTool(MoonLanderGame game) {
        this.game = game;
        backgroundColors = new ArrayList<>();
        backgroundColors.add(Color.GRAY);
        backgroundColors.add(Color.DARKGRAY);
        backgroundColors.add(Color.SILVER);
        backgroundColors.add(Color.LIGHTGRAY);
        backgroundColors.add(Color.WHITE);
        backgroundColors.add(Color.BLACK);
        backgroundColors.add(Color.DARKSLATEGRAY);
        spriteSizeX = 9;
        spriteSizeY = 9;
        createSprite(MoonLanderGame.WIDTH - 8, MoonLanderGame.HEIGHT - 8);
        savedStates = new ArrayList<>();
        int[][] firstSavedState = new int[MoonLanderGame.HEIGHT - 8][MoonLanderGame.WIDTH - 8];
        copyArray(sprite, firstSavedState);
        savedStates.add(firstSavedState);
    }

    // VISUALS

    public void display() {
        drawBackground();
        drawColorPalette();
        drawSprite();
        drawSpriteMask();
        drawTextOverlay();
        Screen.set(Screen.Type.COLOR_PAINTER);
    }

    private void drawBackground() {
        for (int x = 0; x < MoonLanderGame.HEIGHT; x++) {
            for (int y = 0; y < MoonLanderGame.HEIGHT; y++) {
                if (x < 8 || y < 8) {
                    game.setCellValueEx(x, y, Color.BLACK, "", Color.NONE);
                } else {
                    game.setCellValueEx(x, y, backgroundColors.get(0), "", Color.NONE);
                }
            }
        }
    }

    private void drawColorPalette() {
        int colorValue = 0;
        for (int x = 1; x < 6; x++) {
            for (int y = 8; y < MoonLanderGame.HEIGHT; y++) {
                if (colorValue > 148) {
                    continue;
                }
                game.setCellValueEx(x, y, Color.values()[colorValue], String.valueOf(colorValue), Color.WHITE);
                colorValue++;
            }
        }
        game.setCellValueEx(5, 6, Color.values()[brush], String.valueOf(brush), Color.WHITE);
    }

    private void drawSprite() {
        for (int y = 0; y < sprite.length; y++) {
            for (int x = 0; x < sprite[0].length; x++) {
                int value = sprite[y][x];
                game.setCellValueEx(x + 8, y + 8,
                        Color.values()[value],
                        value == 0 ? "" : String.valueOf(sprite[y][x]),
                        Color.WHITE);
            }
        }
    }

    private void drawSpriteMask() {
        int maskX = MoonLanderGame.WIDTH - 8 - spriteSizeX;
        int maskY = MoonLanderGame.HEIGHT - 8 - spriteSizeY;
        for (int x = 8; x < MoonLanderGame.WIDTH; x++) {
            for (int y = MoonLanderGame.HEIGHT - maskY; y < MoonLanderGame.HEIGHT; y++) {
                game.setCellValueEx(x, y, Color.BLACK, "", Color.NONE);
            }
        }
        for (int y = 8; y < MoonLanderGame.HEIGHT; y++) {
            for (int x = MoonLanderGame.WIDTH - maskX; x < MoonLanderGame.WIDTH; x++) {
                game.setCellValueEx(x, y, Color.BLACK, "", Color.NONE);
            }

        }
    }

    private void drawTextOverlay() {
        new Message("JAVARUSH GAMES SPRITE MAKER", Color.YELLOW).draw(game, 0);
        new Message("ARRAY SIZE: " + spriteSizeX + "x" + spriteSizeY, Color.SKYBLUE).draw(game, 1, 2);
        new Message("BACKGROUND: " + backgroundColors.get(0).name(), Color.LIGHTGRAY).draw(game, 1, 4);

        if (fillSelected) {
            new Message("FILL", Color.WHITE).draw(game, 1, 6);
        } else if (replaceSelected) {
            new Message("REPL", Color.WHITE).draw(game, 1, 6);
        } else {
            new Message("DRAW", Color.WHITE).draw(game, 1, 6);
        }
        new Message("(" + Color.values()[brush].name() + ", #" + Color.values()[brush].ordinal() + ")",
                Color.WHITE).draw(game, 7, 6);

        new Message("CLEAR", Color.RED).draw(game, 34, 2);
        new Message("EXPORT", Color.LAWNGREEN).draw(game, 33, 4);
        new Message("UNDO", (savedStates.size() == 0 ? Color.GRAY : Color.YELLOW)).draw(game, 35, 6);
    }


    // ACTIONS

    public void createSprite(int sizeX, int sizeY) {
        this.sprite = new int[sizeY][sizeX];
    }

    public void drawColor(int x, int y) {
        if (clickedOnSprite(x, y)) {
            sprite[y - 8][x - 8] = brush;
            display();
        }
    }

    public void fillColor(int x, int y) {
        if (!clickedOnSprite(x, y)) {
            return;
        }
        int baseColor = sprite[y - 8][x - 8];
        sprite[y - 8][x - 8] = brush;
        if (x - 9 > -1 && sprite[y - 8][x - 9] == baseColor) {
            fillColor(x - 1, y);
        }
        if (x - 7 < (MoonLanderGame.WIDTH - 8) && sprite[y - 8][x - 7] == baseColor) {
            fillColor(x + 1, y);
        }
        if (y - 9 > -1 && sprite[y - 9][x - 8] == baseColor) {
            fillColor(x, y - 1);
        }
        if (y - 7 < (MoonLanderGame.HEIGHT - 8) && sprite[y - 7][x - 8] == baseColor) {
            fillColor(x, y + 1);
        }
        fillSelected = false;
    }

    public void replaceColor(int x, int y) {
        if (clickedOnMask(x, y)) {
            return;
        }
        Color selectedColor = game.getCellColor(x, y);
        for (int j = 0; j < spriteSizeY; j++) {
            for (int i = 0; i < spriteSizeX; i++) {
                if (sprite[j][i] == selectedColor.ordinal()) {
                    sprite[j][i] = brush;
                }
            }
        }
        replaceSelected = false;
        display();
    }

    public void copyColor(int x, int y) {
        if (clickedOnMask(x, y)) {
            return;
        }
        if (x == 1 && y == 8) {
            brush = 0;
        } else if (x > 7 && y > 7 && sprite[y - 8][x - 8] == 0) {
            brush = 0;
        } else {
            Color copiedColor = game.getCellColor(x, y);
            brush = copiedColor.ordinal();
        }
        display();
    }

    public void undo() {
        if (savedStates.size() <= 0) {
            return;
        }
        copyArray(savedStates.get(0), sprite);
        savedStates.remove(0);
        display();
    }

    public void backup() {
        if (savedStates.size() != 0 && sameArray(sprite, savedStates.get(0))) {
            return;
        }
        int[][] saveState = new int[MoonLanderGame.HEIGHT - 8][MoonLanderGame.WIDTH - 8];
        copyArray(sprite, saveState);
        savedStates.add(0, saveState);
        if (savedStates.size() > 20) {
            savedStates.remove(savedStates.size() - 1);
        }
    }

    public boolean sameArray(int[][] original, int[][] copy) {
        for (int y = 0; y < original.length; y++) {
            for (int x = 0; x < original[0].length; x++) {
                if (copy[y][x] != original[y][x]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void changeSpriteSize(Direction direction, int amount) {
        switch (direction) {
            case VERTICAL: {
                spriteSizeY += amount;
                if (spriteSizeY > MoonLanderGame.HEIGHT - 8) {
                    spriteSizeY = MoonLanderGame.HEIGHT - 8;
                } else if (spriteSizeY < 1) {
                    spriteSizeY = 1;
                }
                break;
            }
            case HORIZONTAL: {
                spriteSizeX += amount;
                if (spriteSizeX > MoonLanderGame.WIDTH - 8) {
                    spriteSizeX = MoonLanderGame.WIDTH - 8;
                } else if (spriteSizeX < 1) {
                    spriteSizeX = 1;
                }
                break;
            }
            default: {
                break;
            }
        }
        display();
    }

    public void changeBackground() {
        Color movingColor = backgroundColors.get(0);
        backgroundColors.remove(movingColor);
        backgroundColors.add(movingColor);
        display();
    }

    public void clearSprite() {
        createSprite(MoonLanderGame.WIDTH - 8, MoonLanderGame.HEIGHT - 8);
        display();
    }

    public void copyArray(int[][] original, int[][] copy) {
        for (int y = 0; y < original.length; y++) {
            for (int x = 0; x < original[0].length; x++) {
                copy[y][x] = original[y][x];
            }
        }
    }

    public void exportArray() {
        StringBuilder result = new StringBuilder();
        result.append("new int[][]{");
        for (int y = 0; y < spriteSizeY; y++) {
            for (int x = 0; x < spriteSizeX; x++) {
                if (x == 0) {
                    result.append("{" + sprite[y][x] + ",");
                } else if (x < spriteSizeX - 1) {
                    result.append(sprite[y][x] + ",");
                } else if (x == spriteSizeX - 1) {
                    result.append(sprite[y][x] + "}");
                }
                if (x == spriteSizeX - 1 && y != spriteSizeY - 1) {
                    result.append(",");
                }
            }
        }
        result.append("}");
        String exportedString = result.toString();
        game.setCellValueEx(0, 0, Color.BLACK, exportedString, Color.BLACK, 1);
        game.showMessageDialog(Color.YELLOW, "Inspect HTML elements in your browser\n" +
                "and search for \"nеw int\".", Color.BLACK, 25);
    }

    public void exportArrayToConsole() {
        StringBuilder result = new StringBuilder();
        result.append("new int[][]{");
        for (int y = 0; y < spriteSizeY; y++) {
            for (int x = 0; x < spriteSizeX; x++) {
                if (x == 0) {
                    result.append("\n");
                    result.append("{" + sprite[y][x] + ",");
                } else if (x < spriteSizeX - 1) {
                    result.append(sprite[y][x] + ",");
                } else if (x == spriteSizeX - 1) {
                    result.append(sprite[y][x] + "}");
                }
                if (x == spriteSizeX - 1 && y != spriteSizeY - 1) {
                    result.append(",");
                }
            }
        }
        result.append("\n}");
        String exportedString = result.toString();
        System.out.println(exportedString);
    }

    boolean clickedOnSprite(int x, int y) {
        return (x > 7 && y > 7 && !clickedOnMask(x, y));
    }

    private boolean clickedOnMask(int x, int y) {
        int maskX = MoonLanderGame.WIDTH - 8 - spriteSizeX;
        int maskY = MoonLanderGame.HEIGHT - 8 - spriteSizeY;
        boolean clickedOnBoard = (x > 7 && x < MoonLanderGame.WIDTH && y > 7 && y < MoonLanderGame.HEIGHT);
        boolean clickedOnXMask = (x > MoonLanderGame.WIDTH - 1 - maskX);
        boolean clickedOnYMask = (y > MoonLanderGame.HEIGHT - 1 - maskY);
        return (clickedOnBoard && (clickedOnXMask || clickedOnYMask));
    }


    // INNER CLASSES

    public enum Direction {
        VERTICAL, HORIZONTAL
    }
}



