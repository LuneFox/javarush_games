package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;

public class InputEvent {
    private MoonLanderGame game;

    InputEvent(MoonLanderGame game) {
        this.game = game;
    }

    public void keyPress(Key key) {
        switch (Screen.getCurrent()) {
            case COLOR_PAINTER: {
                switch (key) {
                    case UP: {
                        game.spritePainterTool.changeSpriteSize(SpritePainterTool.Direction.VERTICAL, -1);
                        break;
                    }
                    case DOWN: {
                        game.spritePainterTool.changeSpriteSize(SpritePainterTool.Direction.VERTICAL, 1);
                        break;
                    }
                    case LEFT: {
                        game.spritePainterTool.changeSpriteSize(SpritePainterTool.Direction.HORIZONTAL, -1);
                        break;
                    }
                    case RIGHT: {
                        game.spritePainterTool.changeSpriteSize(SpritePainterTool.Direction.HORIZONTAL, 1);
                        break;
                    }
                    case SPACE: {
                        game.spritePainterTool.changeBackground();
                        break;
                    }
                    case ENTER: {
                        game.spritePainterTool.fillSelected = !game.spritePainterTool.fillSelected;
                        game.spritePainterTool.replaceSelected = false;
                        game.spritePainterTool.display();
                        break;
                    }
                    case ESCAPE: {
                        game.spritePainterTool.replaceSelected = !game.spritePainterTool.replaceSelected;
                        game.spritePainterTool.fillSelected = false;
                        game.spritePainterTool.display();
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    public void leftClick(int x, int y) {
        if (clickOutOfBounds(x, y)) {
            return;
        }
        switch (Screen.getCurrent()) {
            case COLOR_PAINTER: {
                if (x > 7 && y > 7) {
                    game.spritePainterTool.backup();
                    if (game.spritePainterTool.fillSelected) {
                        game.spritePainterTool.fillColor(x, y);
                    } else {
                        game.spritePainterTool.backup();
                        game.spritePainterTool.drawColor(x, y);
                    }
                } else if (x > 33 && x < 39 && y == 2) {
                    game.spritePainterTool.backup();
                    game.spritePainterTool.clearSprite();
                } else if (x > 32 && x < 39 && y == 4) {
                    game.spritePainterTool.exportArray();
                } else if (x > 34 && x < 39 && y == 6) {
                    game.spritePainterTool.undo();
                } else if (x > 0 && x < 16 && y == 2) {
                    game.showMessageDialog(Color.YELLOW, "Use ARROW KEYS to adjust array size.", Color.BLACK, 20);
                } else if (x > 0 && x < 16 && y == 4) {
                    game.showMessageDialog(Color.YELLOW, "Use SPACE KEY to switch background (transparent) color.", Color.BLACK, 20);
                } else if (y == 6) {
                    game.showMessageDialog(Color.YELLOW, "This is current color. Use LEFT CLICK to paint a cell.\n" +
                            "Use ENTER for fill tool. Use ESC for replace tool.", Color.BLACK, 20);
                } else if (x > 1 && x < 7 && y > 7) {
                    game.showMessageDialog(Color.YELLOW, "Use RIGHT CLICK to copy any color on the screen.", Color.BLACK, 20);
                } else if (y == 0) {
                    game.showMessageDialog(Color.ORANGE, "Thanks for using this tool! Hope you like it!\n-- LuneFox. (version: 0.91)", Color.WHITE, 20);
                }
                break;
            }
        }
    }

    public void rightClick(int x, int y) {
        if (clickOutOfBounds(x, y)) {
            return;
        }
        // System.out.println(x + ", " + y);
        switch (Screen.getCurrent()) {
            case COLOR_PAINTER: {
                game.spritePainterTool.backup();
                if (game.spritePainterTool.replaceSelected) {
                    game.spritePainterTool.replaceColor(x, y);
                } else {
                    game.spritePainterTool.copyColor(x, y);
                }
                break;
            }
        }
    }

    public boolean clickOutOfBounds(int x, int y) {
        return (x < 0 || y < 0 || x > MoonLanderGame.WIDTH - 1 || y > MoonLanderGame.HEIGHT - 1);
    }
}
