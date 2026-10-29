package com.javarush.games.spaceinvaders.controller;

import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.Direction;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;

/** Управление Марио в бою; пауза и рестарт передаются игре. */
public class PlayerControlStrategy implements ControlStrategy {
    /** Игра, которой управляет стратегия. */
    private final SpaceInvadersGame game;

    /** Создаёт стратегию для указанной игры. */
    public PlayerControlStrategy(SpaceInvadersGame game) {
        this.game = game;
    }

    /** Заставляет Марио прыгнуть. */
    @Override
    public void upPressed() {
        if (!canControlMario()) return;

        game.getMario().jump();
    }

    /** Направляет Марио вправо. */
    @Override
    public void rightPressed() {
        if (!canControlMario()) return;

        game.getMario().walkTowards(Direction.RIGHT);
    }

    /** Останавливает Марио, если он шёл вправо. */
    @Override
    public void rightReleased() {
        if (game.isRoundOver()) return;
        if (game.getMario().getWalkingDirection() != Direction.RIGHT) return;

        game.getMario().walkTowards(Direction.NONE);
    }

    /** Направляет Марио влево. */
    @Override
    public void leftPressed() {
        if (!canControlMario()) return;

        game.getMario().walkTowards(Direction.LEFT);
    }

    /** Останавливает Марио, если он шёл влево. */
    @Override
    public void leftReleased() {
        if (game.isRoundOver()) return;
        if (game.getMario().getWalkingDirection() != Direction.LEFT) return;

        game.getMario().walkTowards(Direction.NONE);
    }

    /** Использует бонус, а после финала начинает новую игру. */
    @Override
    public void spacePressed() {
        if (game.isRoundOver()) {
            // После финала сначала возвращаемся на главный экран, а не начинаем игру сразу
            if (game.isEndingShown()) game.continueAfterEnding();
            return;
        }
        if (game.isPaused() || game.isIntroActive()) return;

        game.getMario().useBonus();
    }

    /** Переключает паузу. */
    @Override
    public void pausePressed() {
        game.togglePause();
    }

    /** Проверяет, можно ли сейчас управлять Марио. */
    private boolean canControlMario() {
        Mario mario = game.getMario();
        return !game.isPaused() && !game.isRoundOver() && !game.isIntroActive() && mario.isAlive();
    }
}
