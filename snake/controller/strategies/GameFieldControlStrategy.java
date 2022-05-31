package com.javarush.games.snake.controller.strategies;

import com.javarush.games.snake.controller.ControlStrategy;
import com.javarush.games.snake.model.Options;
import com.javarush.games.snake.view.MenuSelector;
import com.javarush.games.snake.model.enums.Direction;
import com.javarush.games.snake.model.Phase;

public class GameFieldControlStrategy implements ControlStrategy {
    private static GameFieldControlStrategy instance;
    private boolean isDelayBeforeAccelerationNeeded;

    public static GameFieldControlStrategy getInstance() {
        if (instance == null) instance = new GameFieldControlStrategy();
        return instance;
    }

    public void reset() {
        isDelayBeforeAccelerationNeeded = true;
    }

    @Override
    public void leftClick(int x, int y) {
        if (game.isStopped()) return;
        game.getSnake().rotateElement(Direction.LEFT);
    }

    @Override
    public void rightClick(int x, int y) {
        if (game.isStopped()) return;
        game.getSnake().rotateElement(Direction.RIGHT);
    }

    @Override
    public void pressUp() {
        if (game.isStopped()) return;
        setHighSpeed();
        game.getSnake().setDirection(Direction.UP);
    }

    @Override
    public void releaseUp() {
        setNormalSpeed();
    }

    @Override
    public void pressDown() {
        if (game.isStopped()) return;
        setHighSpeed();
        game.getSnake().setDirection(Direction.DOWN);
    }

    @Override
    public void releaseDown() {
        setNormalSpeed();
    }

    @Override
    public void pressRight() {
        setHighSpeed();
        if (game.isStopped()) return;
        game.getSnake().setDirection(Direction.RIGHT);
    }

    @Override
    public void releaseRight() {
        setNormalSpeed();
    }

    @Override
    public void pressLeft() {
        setHighSpeed();
        if (game.isStopped()) return;
        game.getSnake().setDirection(Direction.LEFT);
    }

    @Override
    public void releaseLeft() {
        setNormalSpeed();
    }

    @Override
    public void pressSpace() {
        if (game.isStopped()) {
            game.stopTurnTimer();
            Phase.set(Phase.MAIN_MENU);
            MenuSelector.setPointerPosition(0);
            Phase.set(Phase.MAIN_MENU);
        } else {
            game.pause();
        }
    }

    @Override
    public void pressEnter() {
        game.getSnake().rotateElement(Direction.RIGHT);
    }

    @Override
    public void pressEscape() {
        game.getSnake().rotateElement(Direction.LEFT);
    }

    @Override
    public void pressPause() {
        if (game.isStopped()) return;
        game.pause();
    }

    private void setHighSpeed() {
        if (Options.isAccelerationEnabled()) {
            if (isDelayBeforeAccelerationNeeded) {
                game.setNormalTurnDelay();
            } else {
                game.setTurnDelay(50);
            }
            isDelayBeforeAccelerationNeeded = false;
        }
    }

    private void setNormalSpeed() {
        if (Options.isAccelerationEnabled()) {
            isDelayBeforeAccelerationNeeded = true;
            game.setTurnDelay(game.getNormalTurnDelay());
        }
    }
}
