package com.javarush.games.moonlander;

import com.javarush.engine.cell.Color;

public class SpritePainterTool {
    private MoonLanderGame game;
    private int brush;
    private int[][] sprite;

    SpritePainterTool(MoonLanderGame game) {
        this.game = game;
        createBitMap(32, 32);
    }

    // VISUALS

    public void display() {
        drawBackground();
        drawColorPalette();
        drawSprite();
        new Message("JAVARUSH GAMES SPRITE MAKER", Color.YELLOW).draw(game, 0);
        Screen.set(Screen.Type.COLOR_PAINTER);
    }

    private void drawBackground() {
        for (int x = 0; x < MoonLanderGame.HEIGHT; x++) {
            for (int y = 0; y < MoonLanderGame.HEIGHT; y++) {
                if (x < 8 || y < 8) {
                    game.setCellValueEx(x, y, Color.BLACK, "", Color.NONE);
                } else {
                    game.setCellValueEx(x, y, Color.GRAY, "", Color.NONE);
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

        new Message("NOW:", Color.WHITE).draw(game, 1, 6);
        game.setCellValueEx(5, 6, Color.values()[brush], String.valueOf(brush), Color.WHITE);
        new Message("(" + Color.values()[brush].name() + ", #" + Color.values()[brush].ordinal() + ")",
                Color.WHITE).draw(game, 7, 6);
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


    // ACTIONS

    public void createBitMap(int sizeX, int sizeY) {
        this.sprite = new int[sizeY][sizeX];
    }

    public void pasteColor(int x, int y) {
        sprite[y - 8][x - 8] = brush;
        display();
    }

    public void copyColor(int x, int y) {
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
}
