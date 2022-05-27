package com.javarush.games.game2048.controller;

import com.javarush.games.game2048.Game2048;
import com.javarush.games.game2048.model.Direction;

public class MainControlStrategy implements ControlStrategy {
    private final Game2048 game;

    public MainControlStrategy(Game2048 game) {
        this.game = game;
    }

    @Override
    public void leftClick(int x, int y) {
        if (game.isStopped()) return;

        if (y == 0 && x > 0 && x < 6) releaseUp();
        else if (y == 6 && x > 0 && x < 6) releaseDown();
        else if (x == 0 && y > 0 && y < 6) releaseLeft();
        else if (x == 6 && y > 0 && y < 6) releaseRight();
        else if (x == 0 && y == 0) game.showHelpDialog();
    }

    @Override
    public void rightClick(int x, int y) {
        if (game.isStopped()) return;

        if (x == 0 && y == 1) game.emptyPocket(0);
        else if (x == 0 && y == 5) game.emptyPocket(1);
        else if (x == 6 && y == 1) game.emptyPocket(2);
        else if (x == 6 && y == 5) game.emptyPocket(3);
        else if (x == 3 && y == 0) game.emptyPocket(4);
        else if (x == 3 && y == 6) game.emptyPocket(5);
        else if (x > 0 && x < 6 && y > 0 && y < 6) {
            game.placeOrRemoveWhiteBall(x, y);
        }
    }

    @Override
    public void releaseUp() {
        if (game.isStopped()) return;
        game.move(Direction.UP);
    }

    @Override
    public void releaseDown() {
        if (game.isStopped()) return;
        game.move(Direction.DOWN);
    }

    @Override
    public void releaseRight() {
        if (game.isStopped()) return;
        game.move(Direction.RIGHT);
    }

    @Override
    public void releaseLeft() {
        if (game.isStopped()) return;
        game.move(Direction.LEFT);
    }

    @Override
    public void pressSpace() {
        if (!game.isStopped()) return;
        game.createNewGame();
    }

    @Override
    public void pressAnyOtherKey() {
        game.finishIfResultIsAchieved();
    }

    @Override
    public void pressEnter() {
        performRandomMove();
    }

    private void performRandomMove() {
        double random = Math.random();

        if (random < 0.25) {
            releaseUp();
        } else if (random < 0.5) {
            releaseDown();
        } else if (random < 0.75) {
            releaseLeft();
        } else {
            releaseRight();
        }
    }
}
