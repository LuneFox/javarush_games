package com.javarush.games.game2048;

import com.javarush.engine.cell.*;

class InputEvent {
    final private Game2048 game;

    InputEvent(Game2048 game) {
        this.game = game;
    }

    final void keyRelease(Key key) {
        if (game.isStopped) {
            if (key != Key.SPACE && key != Key.ENTER) {
                if (game.lastResultVictory) {
                    game.win();
                } else {
                    game.gameOver("Пробел — начать заново.");
                }
                return;
            }
            game.reset();
            return;
        }
        if (!game.canUserMove()) {
            game.gameOver("Невозможно совершить ход!");
            return;
        }
        switch (key) {
            case LEFT:
                game.moveLeft();
                game.drawScene();
                break;
            case RIGHT:
                game.moveRight();
                game.drawScene();
                break;
            case UP:
                game.moveUp();
                game.drawScene();
                break;
            case DOWN:
                game.moveDown();
                game.drawScene();
                break;
            default:
                break;
        }
    }

    final void leftClick(int x, int y) {
        if (y == 0 && x > 0 && x < 6) {
            keyRelease(Key.UP);
        } else if (y == 6 && x > 0 && x < 6) {
            keyRelease(Key.DOWN);
        } else if (x == 0 && y > 0 && y < 6) {
            keyRelease(Key.LEFT);
        } else if (x == 6 && y > 0 && y < 6) {
            keyRelease(Key.RIGHT);
        }
    }

    final void rightClick(int x, int y) {
        if (game.isStopped) {
            return;
        }

        if (x == 0 && y == 1) {
            game.emptyPocket(0);
            game.drawScene();
        } else if (x == 0 && y == 5) {
            game.emptyPocket(1);
            game.drawScene();
        } else if (x == 6 && y == 1) {
            game.emptyPocket(2);
            game.drawScene();
        } else if (x == 6 && y == 5) {
            game.emptyPocket(3);
            game.drawScene();
        } else if (x == 3 && y == 0) {
            game.emptyPocket(4);
            game.drawScene();
        } else if (x == 3 && y == 6) {
            game.emptyPocket(5);
            game.drawScene();
        } else if (x > 0 && x < 6 && y > 0 && y < 6) {

            int[][] field = game.getField();

            if (game.whiteBallSet && field[y][x] == 16) {
                field[y][x] = 0;
                game.whiteBallSet = false;
                game.drawScene();
                return;
            }

            if (!game.whiteBallSet && field[y][x] == 0) {
                field[y][x] = 16;
                game.whiteBallSet = true;
                game.drawScene();
            }
        }
    }
}