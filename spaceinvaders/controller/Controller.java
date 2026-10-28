package com.javarush.games.spaceinvaders.controller;

import com.javarush.engine.cell.Key;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;

/** Переводит клавиши движка в события управления. */
public class Controller {
    /** Стратегия обработки клавиш. */
    private final ControlStrategy strategy;

    /** Создаёт контроллер с управлением Марио. */
    public Controller(SpaceInvadersGame game) {
        this.strategy = new PlayerControlStrategy(game);
    }

    /** Обрабатывает нажатие клавиши. */
    public void handleKeyPress(Key key) {
        switch (key) {
            case UP:
                strategy.upPressed();
                break;
            case DOWN:
                strategy.downPressed();
                break;
            case LEFT:
                strategy.leftPressed();
                break;
            case RIGHT:
                strategy.rightPressed();
                break;
            case SPACE:
                strategy.spacePressed();
                break;
            case PAUSE:
                strategy.pausePressed();
                break;
            default:
                break;
        }
    }

    /** Обрабатывает отпускание клавиши. */
    public void handleKeyRelease(Key key) {
        switch (key) {
            case UP:
                strategy.upReleased();
                break;
            case DOWN:
                strategy.downReleased();
                break;
            case LEFT:
                strategy.leftReleased();
                break;
            case RIGHT:
                strategy.rightReleased();
                break;
            case SPACE:
                strategy.spaceReleased();
                break;
            case PAUSE:
                strategy.pauseReleased();
                break;
            default:
                break;
        }
    }
}
