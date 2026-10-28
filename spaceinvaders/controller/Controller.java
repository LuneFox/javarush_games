package com.javarush.games.spaceinvaders.controller;

import com.javarush.engine.cell.Key;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;

/**
 * Translates engine keys into control events. The only place that knows which key means what.
 */
public class Controller {
    private final ControlStrategy strategy;

    public Controller(SpaceInvadersGame game) {
        this.strategy = new PlayerControlStrategy(game);
    }

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
