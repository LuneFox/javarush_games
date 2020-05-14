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

    void keyPress(Key key) { // TODO: returns!!
        keyPressInMenu(key);
        keyPressInGame(key);
    }

    void keyRelease(Key key) {
        keyReleaseInGame(key);
    }

    void leftClick(int x, int y) {
        leftClickInGame(x, y);
    }

    void rightClick(int x, int y) {
        rightClickInGame(x, y);
    }


    // DETAILED

    private void keyPressInMenu(Key key) {
        if (Screen.is(Screen.Type.MAIN_MENU)) {
            switch (key) {
                case SPACE:
                    menu.displayGame();
                    this.snake = game.getSnake();
                    break;
                case UP:
                    Signs.set(Graphics.KANJI);
                    menu.displayMain();
                    break;
                case DOWN:
                    Signs.set(Graphics.EMOJI);
                    menu.displayMain();
                    break;
                default:
                    break;
            }
        }
    }

    private void keyPressInGame(Key key) {
        if (Screen.is(Screen.Type.GAME)) {
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
            } else { // if game is stopped
                if (key == Key.SPACE) {
                    menu.displayGame();
                    this.snake = game.getSnake();
                }
            }
        }
    }

    private void keyReleaseInGame(Key key) {
        if (Screen.is(Screen.Type.GAME) && isDirectionalKey(key)) {
            if (isDirectionalKey(key)) {
                speedDown();
            }
        }
    }

    private void leftClickInGame(int x, int y) {
        if (Screen.is(Screen.Type.GAME) && !game.isStopped()) {
            snake.rotateToNextElement();
        }
    }

    private void rightClickInGame(int x, int y) {
        if (Screen.is(Screen.Type.GAME) && !game.isStopped()) {
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