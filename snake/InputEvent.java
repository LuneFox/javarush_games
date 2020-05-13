package com.javarush.games.snake;

import com.javarush.engine.cell.*;
import com.javarush.games.snake.enums.*;

import static com.javarush.games.snake.Triggers.*;

class InputEvent {
    private SnakeGame game;
    private Snake snake;

    InputEvent(SnakeGame game) {
        this.game = game;
    }

    void keyPress(Key key) {
        if (firstLaunch) {
            switch (key) {
                case SPACE:
                    firstLaunch = false;
                    game.createGame();
                    this.snake = game.getSnake();
                    break;
                case UP:
                    Signs.setSigns(Graphics.KANJI);
                    game.displayHelp();
                    break;
                case DOWN:
                    Signs.setSigns(Graphics.EMOJI);
                    game.displayHelp();
                    break;
                default:
                    break;
            }
        } else if (!game.isStopped()) {
            if (speedUpDelay) {
                game.setTurnDelay(Math.max((SnakeGame.MAX_TURN_DELAY - (snake.getLength() * 10)), 100));
                Triggers.speedUpDelay = false;
            } else {
                if (isDirectionalKey(key)) {
                    game.setTurnDelay(50);
                }
            }
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
                    snake.swapNextElement();
                    break;
                case ESCAPE:
                    snake.swapPreviousElement();
                    break;
                default:
                    break;
            }
        } else {
            if (key == Key.SPACE) {
                game.createGame();
                this.snake = game.getSnake();
            }
        }

    }

    void keyRelease(Key key) {
        if ((!firstLaunch) && (isDirectionalKey(key))) {
            Triggers.speedUpDelay = true;
            game.setTurnDelay(Math.max((SnakeGame.MAX_TURN_DELAY - (snake.getLength() * 10)), 100));
        }
    }

    void leftClick(int x, int y) {
        if (!firstLaunch) snake.swapNextElement();
    }

    void rightClick(int x, int y) {
        if (!firstLaunch) snake.swapPreviousElement();
    }

    private boolean isDirectionalKey(Key key) {
        return key == Key.UP || key == Key.RIGHT || key == Key.LEFT || key == Key.DOWN;
    }
}