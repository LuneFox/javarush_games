package com.javarush.games.snake.controller.strategies;

import com.javarush.games.snake.controller.ControlStrategy;
import com.javarush.games.snake.model.Options;
import com.javarush.games.snake.model.Phase;
import com.javarush.games.snake.model.enums.Direction;
import com.javarush.games.snake.view.MenuSelector;

/** Управление змейкой и паузой на игровом поле. */
public class GameFieldControlStrategy implements ControlStrategy {
    private static final int ACCELERATED_TURN_DELAY = 50;

    private static GameFieldControlStrategy instance;
    private boolean isFirstDirectionPress;

    /** Возвращает единственный экземпляр. */
    public static GameFieldControlStrategy getInstance() {
        if (instance == null) instance = new GameFieldControlStrategy();
        return instance;
    }

    /** Сбрасывает состояние ускорения перед новой игрой. */
    public void reset() {
        isFirstDirectionPress = true;
    }

    /** Поворачивает стихию влево. */
    @Override
    public void leftClick(int x, int y) {
        game.getSnake().rotateElement(Direction.LEFT);
    }

    /** Поворачивает стихию вправо. */
    @Override
    public void rightClick(int x, int y) {
        game.getSnake().rotateElement(Direction.RIGHT);
    }

    /** Задаёт движение вверх и ускоряет змейку. */
    @Override
    public void pressUp() {
        pressDirection(Direction.UP);
    }

    /** Задаёт движение вниз и ускоряет змейку. */
    @Override
    public void pressDown() {
        pressDirection(Direction.DOWN);
    }

    /** Задаёт движение вправо и ускоряет змейку. */
    @Override
    public void pressRight() {
        pressDirection(Direction.RIGHT);
    }

    /** Задаёт движение влево и ускоряет змейку. */
    @Override
    public void pressLeft() {
        pressDirection(Direction.LEFT);
    }

    /** Возвращает обычную скорость. */
    @Override
    public void releaseUp() {
        releaseDirection();
    }

    /** Возвращает обычную скорость. */
    @Override
    public void releaseDown() {
        releaseDirection();
    }

    /** Возвращает обычную скорость. */
    @Override
    public void releaseRight() {
        releaseDirection();
    }

    /** Возвращает обычную скорость. */
    @Override
    public void releaseLeft() {
        releaseDirection();
    }

    /** Запускает уровень, ставит на паузу или выходит в меню. */
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

    /** Переключает стихию вперёд. */
    @Override
    public void pressEnter() {
        game.getSnake().rotateElement(Direction.RIGHT);
    }

    /** Переключает стихию назад. */
    @Override
    public void pressEscape() {
        game.getSnake().rotateElement(Direction.LEFT);
    }

    /** Ставит игру на паузу или снимает её. */
    @Override
    public void pressPause() {
        if (game.isStopped()) return;

        game.togglePause();
    }

    /** Задаёт направление и применяет ускорение. */
    private void pressDirection(Direction direction) {
        if (game.isStopped()) return;

        applyAcceleration();
        game.getSnake().setDirection(direction);
    }

    /** Возвращает обычную задержку хода. */
    private void releaseDirection() {
        if (!Options.isAccelerationEnabled()) return;

        isFirstDirectionPress = true;
        game.setTurnDelay(game.getNormalTurnDelay());
    }

    /** Уменьшает задержку хода при удержании направления. */
    private void applyAcceleration() {
        if (!Options.isAccelerationEnabled()) return;

        int delay = isFirstDirectionPress ? game.getNormalTurnDelay() : ACCELERATED_TURN_DELAY;
        game.setTurnDelay(delay);
        isFirstDirectionPress = false;
    }

    /** Останавливает таймер и возвращает в главное меню. */
    private void returnToMainMenu() {
        game.stopTurnTimer();
        MenuSelector.setPointerPosition(0);
        Phase.proceed(Phase.MAIN_MENU);
    }
}
