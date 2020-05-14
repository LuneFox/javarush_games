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
        if (Screen.is(Screen.Type.GAME)) {
            keyPressInGame(key);
        } else if (Screen.is(Screen.Type.MAIN_MENU)) {
            keyPressInMenu(key);
        } else if (Screen.is(Screen.Type.OPTIONS)) {
            keyPressInOptions(key);
        } else if (Screen.is(Screen.Type.CONTROLS)) {
            keyPressInControls(key);
        } else if (Screen.is(Screen.Type.HELP)) {
            keyPressInHelp(key);
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
        }
    }

    void rightClick(int x, int y) {
        if (Screen.is(Screen.Type.GAME)) {
            rightClickInGame(x, y);
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
                    menu.displayOptions();
                } else if (Menu.Selector.nowAt("MAP")) {
                    menu.selectStageUp();
                    menu.displayOptions();
                }
                break;
            case LEFT:
                if (Menu.Selector.nowAt("SYMBOLS")) {
                    menu.switchSymbolSet();
                    menu.displayOptions();
                } else if (Menu.Selector.nowAt("MAP")) {
                    menu.selectStageDown();
                    menu.displayOptions();
                }
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
                    snake.rotateToNextElement();
                    break;
                case ESCAPE:
                    snake.rotateToPreviousElement();
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

    private void keyReleaseInGame(Key key) {
        if (isDirectionalKey(key)) {
            speedDown();
        }
    }

    private void leftClickInGame(int x, int y) {
        if (!game.isStopped()) {
            snake.rotateToNextElement();
        }
    }

    private void rightClickInGame(int x, int y) {
        if (!game.isStopped()) {
            snake.rotateToPreviousElement();
        }
    }


    // UTILITY

    private boolean isDirectionalKey(Key key) {
        return key == Key.UP || key == Key.RIGHT || key == Key.LEFT || key == Key.DOWN;
    }

    private void speedUp(Key key) {
        if (Triggers.speedUpDelay) { // normal, slow step for the first time
            game.setTurnDelay();
            Triggers.speedUpDelay = false;
        } else {                     // speed up if the user keeps holding the key
            if (isDirectionalKey(key)) {
                game.setTurnDelay(50);
            }
        }
    }

    private void speedDown() {
        // returns to normal speed when user releases any directional key
        Triggers.speedUpDelay = true;
        game.setTurnDelay(Math.max((SnakeGame.MAX_TURN_DELAY - (snake.getLength() * 10)), 100));
    }
}