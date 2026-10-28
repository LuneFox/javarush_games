package com.javarush.games.spaceinvaders;

import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.spaceinvaders.controller.Controller;
import com.javarush.games.spaceinvaders.model.Arena;
import com.javarush.games.spaceinvaders.model.GameField;
import com.javarush.games.spaceinvaders.model.GameStatus;
import com.javarush.games.spaceinvaders.model.Score;
import com.javarush.games.spaceinvaders.model.StageProgress;
import com.javarush.games.spaceinvaders.model.gameobjects.GameObject;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.EnemyArmy;
import com.javarush.games.spaceinvaders.model.gameobjects.battlers.Mario;
import com.javarush.games.spaceinvaders.model.gameobjects.bullets.Bullet;
import com.javarush.games.spaceinvaders.model.gameobjects.items.bricks.Brick;
import com.javarush.games.spaceinvaders.view.EndingScreen;
import com.javarush.games.spaceinvaders.view.PixelScreen;
import com.javarush.games.spaceinvaders.view.Scenery;
import com.javarush.games.spaceinvaders.view.ScoreBoard;
import com.javarush.games.spaceinvaders.view.ScreenFlash;
import com.javarush.games.spaceinvaders.view.printer.SymbolImage;

import java.util.List;

/**
 * Engine adapter of the game: creates the collaborators, runs one turn and forwards the keys.
 * Everything else is delegated to the arena, the score board and the ending screen.
 */
public class SpaceInvadersGame extends Game {
    private static final int TURN_DELAY_MS = 40;
    private static final int TURNS_BEFORE_ENDING = 60;

    private Arena arena;
    private PixelScreen screen;
    private Scenery scenery;
    private ScreenFlash flash;
    private ScoreBoard scoreBoard;
    private EndingScreen endingScreen;
    private Controller controller;

    private GameStatus status = GameStatus.RUNNING;
    private int turnsAfterRoundEnd;

    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(GameField.WIDTH, GameField.HEIGHT);

        GameObject.setGame(this);

        screen = new PixelScreen(this);
        SymbolImage.setScreen(screen);
        scenery = new Scenery(this);
        flash = new ScreenFlash(this);
        scoreBoard = new ScoreBoard();
        endingScreen = new EndingScreen(this);
        controller = new Controller(this);

        startNewGame();
    }

    /**
     * Starts the first stage or the next one, depending on how the previous stage ended.
     */
    public void startNewGame() {
        moveToNextStage();

        arena = new Arena(this);
        status = GameStatus.RUNNING;
        turnsAfterRoundEnd = 0;
        endingScreen.reset();

        setTurnTimer(TURN_DELAY_MS);
        render();
    }

    private void moveToNextStage() {
        if (arena != null && arena.getMario().isAlive()) {
            StageProgress.advance();
        } else {
            Score.reset();
            StageProgress.restart();
        }
    }

    @Override
    public void onTurn(int step) {
        if (!isPaused()) {
            arena.update();
            arena.resolveCollisions();
            updateRound();
            arena.removeDestroyedObjects();
            setScore(Score.getScore());
        }

        render();
    }

    private void updateRound() {
        if (arena.hasArmyReachedTheBrickLine()) {
            arena.getMario().takeHit();
        }

        if (arena.isMarioDefeated()) {
            arena.getMario().playDeathAnimation();
            finishRound();
        } else if (arena.isArmyDestroyed()) {
            arena.getMario().playVictoryAnimation();
            finishRound();
        }
    }

    private void finishRound() {
        status = GameStatus.OVER;
        turnsAfterRoundEnd++;

        if (turnsAfterRoundEnd < TURNS_BEFORE_ENDING) return;
        if (endingScreen.isShown()) return;

        if (arena.getMario().isAlive()) {
            endingScreen.showVictory();
        } else {
            endingScreen.showDefeat();
        }
    }

    private void render() {
        scenery.draw();
        scoreBoard.draw();
        arena.drawObjects();
        flash.draw();
        scoreBoard.drawPauseLabelIfPaused(isPaused());
        screen.draw();
    }

    /*
     * Game state
     */

    public void togglePause() {
        if (status == GameStatus.OVER) return;

        status = (status == GameStatus.PAUSED) ? GameStatus.RUNNING : GameStatus.PAUSED;
    }

    public boolean isPaused() {
        return status == GameStatus.PAUSED;
    }

    public boolean isRoundOver() {
        return status == GameStatus.OVER;
    }

    public boolean isEndingShown() {
        return endingScreen.isShown();
    }

    /*
     * Controls
     */

    @Override
    public void onKeyPress(Key key) {
        controller.handleKeyPress(key);
    }

    @Override
    public void onKeyReleased(Key key) {
        controller.handleKeyRelease(key);
    }

    /*
     * Accessors used by game objects and the controller
     */

    public Arena getArena() {
        return arena;
    }

    public Mario getMario() {
        return arena.getMario();
    }

    public EnemyArmy getEnemyArmy() {
        return arena.getEnemyArmy();
    }

    public List<Brick> getBricks() {
        return arena.getBricks();
    }

    public List<Bullet> getEnemyBullets() {
        return arena.getEnemyBullets();
    }

    public void addPlayerBullet(Bullet bullet) {
        arena.addPlayerBullet(bullet);
    }

    public PixelScreen getScreen() {
        return screen;
    }

    public ScreenFlash getFlash() {
        return flash;
    }
}
