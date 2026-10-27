package com.javarush.games.snake.controller.strategies;

import com.javarush.games.snake.controller.ControlStrategy;
import com.javarush.games.snake.model.Options;
import com.javarush.games.snake.model.Phase;
import com.javarush.games.snake.model.enums.Direction;
import com.javarush.games.snake.view.MenuSelector;

public class GameFieldControlStrategy implements ControlStrategy {
    private static final int ACCELERATED_TURN_DELAY = 50;

    private static GameFieldControlStrategy instance;
    private boolean isFirstDirectionPress;

    public static GameFieldControlStrategy getInstance() {
        if (instance == null) instance = new GameFieldControlStrategy();
        return instance;
    }

    public void reset() {
        isFirstDirectionPress = true;
    }

    @Override
    public void leftClick(int x, int y) {
        game.getSnake().rotateElement(Direction.LEFT);
    }

    @Override
    public void rightClick(int x, int y) {
        game.getSnake().rotateElement(Direction.RIGHT);
    }

    @Override
    public void pressUp() {
        pressDirection(Direction.UP);
    }

    @Override
    public void pressDown() {
        pressDirection(Direction.DOWN);
    }

    @Override
    public void pressRight() {
        pressDirection(Direction.RIGHT);
    }

    @Override
    public void pressLeft() {
        pressDirection(Direction.LEFT);
    }

    @Override
    public void releaseUp() {
        releaseDirection();
    }

    @Override
    public void releaseDown() {
        releaseDirection();
    }

    @Override
    public void releaseRight() {
        releaseDirection();
    }

    @Override
    public void releaseLeft() {
        releaseDirection();
    }

    @Override
    public void pressSpace() {
        if (!game.getStage().isStarted()) {
            game.getStage().start();
        } else if (game.isStopped()) {
            returnToMainMenu();
        } else {
            game.togglePause();
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

        game.togglePause();
    }

    private void pressDirection(Direction direction) {
        if (game.isStopped()) return;

        applyAcceleration();
        game.getSnake().setDirection(direction);
    }

    private void releaseDirection() {
        if (!Options.isAccelerationEnabled()) return;

        isFirstDirectionPress = true;
        game.setTurnDelay(game.getNormalTurnDelay());
    }

    private void applyAcceleration() {
        if (!Options.isAccelerationEnabled()) return;

        int delay = isFirstDirectionPress ? game.getNormalTurnDelay() : ACCELERATED_TURN_DELAY;
        game.setTurnDelay(delay);
        isFirstDirectionPress = false;
    }

    private void returnToMainMenu() {
        game.stopTurnTimer();
        MenuSelector.setPointerPosition(0);
        Phase.proceed(Phase.MAIN_MENU);
    }
}
