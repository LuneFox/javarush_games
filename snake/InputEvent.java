package com.javarush.games.snake;

import com.javarush.engine.cell.*;
import com.javarush.games.snake.enums.*;

class InputEvent {
    private SnakeGame game;
    private Snake snake;
    private Menu menu;

    InputEvent(SnakeGame game) {
        this.game = game;
        this.menu = game.getMenu();
    }

    // GENERAL

    void keyPress(Key key) {
        switch (Screen.getCurrent()) {
            case GAME:
                keyPressInGame(key);
                break;
            case MAIN_MENU:
                keyPressInMenu(key);
                break;
            case OPTIONS:
                keyPressInOptions(key);
                break;
            case CONTROLS:
                keyPressInControls(key);
                break;
            case HELP:
                keyPressInHelp(key);
                break;
            case MAP_EDIT:
                keyPressInMapEditor(key);
                break;
            default:
                break;
        }
    }

    void keyRelease(Key key) {
        if (Screen.is(Screen.Type.GAME)) {
            keyReleaseInGame(key);
        }
    }

    void leftClick(int x, int y) {
        if (Screen.is(Screen.Type.GAME)) {
            leftClickInGame(x, y);
        } else if (Screen.is(Screen.Type.MAP_EDIT)) {
            leftClickInMapEditor(x, y);
        }
    }

    void rightClick(int x, int y) {
        if (Screen.is(Screen.Type.GAME)) {
            rightClickInGame(x, y);
        } else if (Screen.is(Screen.Type.MAP_EDIT)) {
            rightClickInMapEditor(x, y);
        }
    }


    // DETAILED

    private void keyPressInMenu(Key key) {
        switch (key) {
            case UP:
                Menu.Selector.selectUp();
                menu.displayMain();
                break;
            case DOWN:
                Menu.Selector.selectDown();
                menu.displayMain();
                break;
            case SPACE:
            case ENTER:
                if (Menu.Selector.nowAt("START")) {
                    menu.lastPointerPosition = Menu.Selector.getPointer();
                    menu.startGame();
                    this.snake = game.getSnake();
                    break;
                } else if (Menu.Selector.nowAt("OPTIONS")) {
                    menu.lastPointerPosition = Menu.Selector.getPointer();
                    Menu.Selector.setPointer(0);
                    menu.displayOptions();
                    break;
                } else if (Menu.Selector.nowAt("CONTROLS")) {
                    menu.lastPointerPosition = Menu.Selector.getPointer();
                    Menu.Selector.setPointer(0);
                    menu.displayControls();
                    break;
                } else if (Menu.Selector.nowAt("HELP")) {
                    menu.lastPointerPosition = Menu.Selector.getPointer();
                    Menu.Selector.setPointer(0);
                    menu.displayHelp();
                    break;
                } else if (Menu.Selector.nowAt("EDIT")) {
                    menu.lastPointerPosition = Menu.Selector.getPointer();
                    Menu.Selector.setPointer(0);
                    game.setMap(game.getStage());
                    menu.displayMapEditor();
                    break;
                }
            default:
                break;
        }
    }

    private void keyPressInOptions(Key key) {
        switch (key) {
            case UP:
                Menu.Selector.selectUp();
                menu.displayOptions();
                break;
            case DOWN:
                Menu.Selector.selectDown();
                menu.displayOptions();
                break;
            case SPACE:
            case ENTER:
            case RIGHT:
                if (Menu.Selector.nowAt("SYMBOLS")) {
                    menu.switchSymbolSet();
                } else if (Menu.Selector.nowAt("MAP")) {
                    menu.selectStageUp();
                } else if (Menu.Selector.nowAt("ACCELERATION")) {
                    game.acceleration = !game.acceleration;
                }
                menu.displayOptions();
                break;
            case LEFT:
                if (Menu.Selector.nowAt("SYMBOLS")) {
                    menu.switchSymbolSet();
                } else if (Menu.Selector.nowAt("MAP")) {
                    menu.selectStageDown();
                } else if (Menu.Selector.nowAt("ACCELERATION")) {
                    game.acceleration = !game.acceleration;
                }
                menu.displayOptions();
                break;
            case ESCAPE:
                Menu.Selector.setPointer(menu.lastPointerPosition);
                menu.displayMain();
                break;
            default:
                break;
        }
    }

    private void keyPressInControls(Key key) {
        if (key == Key.ESCAPE) {
            Menu.Selector.setPointer(menu.lastPointerPosition);
            menu.displayMain();
        }
    }

    private void keyPressInHelp(Key key) {
        if (key == Key.ESCAPE) {
            Menu.Selector.setPointer(menu.lastPointerPosition);
            menu.displayMain();
        } else if (key == Key.RIGHT) {
            menu.nextHelpPage();
        } else if (key == Key.LEFT) {
            menu.previousHelpPage();
        }
    }

    private void keyPressInGame(Key key) {
        if (!game.isStopped()) {
            speedUp(key);
            switch (key) {
                case UP:
                    snake.setDirection(Direction.UP);
                    break;
                case RIGHT:
                    snake.setDirection(Direction.RIGHT);
                    break;
                case DOWN:
                    snake.setDirection(Direction.DOWN);
                    break;
                case LEFT:
                    snake.setDirection(Direction.LEFT);
                    break;
                case ENTER:
                    snake.rotateToNextElement(game);
                    break;
                case ESCAPE:
                    snake.rotateToPreviousElement(game);
                    break;
                case SPACE:
                    game.pause();
                    break;
                default:
                    break;
            }
        } else {
            // If game is stopped
            if (key == Key.SPACE) {
                game.stopTurnTimer();
                Screen.set(Screen.Type.MAIN_MENU);
                Menu.Selector.setPointer(0);
                menu.displayMain();
            }
        }
    }

    private void keyPressInMapEditor(Key key) {
        switch (key) {
            case ESCAPE:
                Menu.Selector.setPointer(menu.lastPointerPosition);
                menu.displayMain();
                break;
            case UP:
            case RIGHT:
                menu.brushNext();
                menu.displayMapEditor();
                break;
            case DOWN:
            case LEFT:
                menu.brushPrevious();
                menu.displayMapEditor();
                break;
            case ENTER:
                menu.printTerrain();
                break;
        }
    }

    private void keyReleaseInGame(Key key) {
        if (isDirectionalKey(key)) {
            speedDown();
        }
    }

    private void leftClickInGame(int x, int y) {
        if (!game.isStopped()) {
            snake.rotateToPreviousElement(game);
        }
    }

    private void rightClickInGame(int x, int y) {
        if (!game.isStopped()) {
            snake.rotateToNextElement(game);
        }
    }

    private void leftClickInMapEditor(int x, int y) {
        if (game.outOfBounds(x, y)) {
            return;
        }
        menu.drawTerrain(x, y);
        menu.displayMapEditor();
    }

    private void rightClickInMapEditor(int x, int y) {
        if (game.outOfBounds(x, y)) {
            return;
        }
        menu.printCoordinate(x, y);
        menu.copyTerrain(x, y);
        menu.displayMapEditor();
    }


    // UTILITY

    private boolean isDirectionalKey(Key key) {
        return key == Key.UP || key == Key.RIGHT || key == Key.LEFT || key == Key.DOWN;
    }

    private void speedUp(Key key) {
        if (isDirectionalKey(key)) {
            if (game.acceleration) {
                if (game.speedUpDelay) { // normal, slow step for the first time
                    game.setTurnDelay();
                    game.speedUpDelay = false;
                } else {                     // speed up if the user keeps holding the key
                    game.setTurnDelay(50);
                    game.speedUpDelay = false;
                }
            }
        }
    }

    private void speedDown() {
        // returns to normal speed when user releases any directional key
        if (game.acceleration) {
            game.speedUpDelay = true;
            game.setTurnDelay(game.getSpeed());
        }
    }
}