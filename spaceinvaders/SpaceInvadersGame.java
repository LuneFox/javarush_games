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

/** Адаптер игры к движку: создаёт участников, проводит ход и передаёт клавиши. */
public class SpaceInvadersGame extends Game {
    /** Задержка между ходами в миллисекундах. */
    private static final int TURN_DELAY_MS = 40;
    /** Число ходов после конца раунда до показа финального диалога. */
    private static final int TURNS_BEFORE_ENDING = 60;

    /** Текущее поле боя. */
    private Arena arena;
    /** Пиксельный буфер экрана. */
    private PixelScreen screen;
    /** Фон игры. */
    private Scenery scenery;
    /** Эффект вспышки. */
    private ScreenFlash flash;
    /** Табло со счётом и стадией. */
    private ScoreBoard scoreBoard;
    /** Финальный диалог. */
    private EndingScreen endingScreen;
    /** Обработчик управления. */
    private Controller controller;

    /** Текущее состояние игры. */
    private GameStatus status = GameStatus.RUNNING;
    /** Счётчик ходов после окончания раунда. */
    private int turnsAfterRoundEnd;

    /** Инициализирует игру при запуске. */
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

    /** Начинает новую игру: следующую стадию или первую после поражения. */
    public void startNewGame() {
        moveToNextStage();

        arena = new Arena(this);
        scenery.resetParallax();
        status = GameStatus.RUNNING;
        turnsAfterRoundEnd = 0;
        endingScreen.reset();

        setTurnTimer(TURN_DELAY_MS);
        render();
    }

    /** Переводит счётчик стадий вперёд или сбрасывает его. */
    private void moveToNextStage() {
        if (arena != null && arena.getMario().isAlive()) {
            StageProgress.advance();
        } else {
            Score.reset();
            StageProgress.restart();
        }
    }

    /** Проводит один ход: обновляет бой и рисует кадр. */
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

    /** Проверяет условия конца раунда и включает финальную анимацию. */
    private void updateRound() {
        if (arena.hasArmyReachedTheBrickLine()) {
            arena.getMario().takeHit();
        }

        if (arena.isMarioDefeated()) {
            arena.getMario().playDeathAnimation();
            finishRound();
        } else if (arena.isLevelCompleted()) {
            arena.getMario().playVictoryAnimation();
            finishRound();
        }
    }

    /** Отмечает конец раунда и показывает финал после задержки. */
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

    /** Рисует кадр: сцену, интерфейс, объекты боя и вспышку. */
    private void render() {
        scenery.draw();
        scoreBoard.draw();
        arena.drawObjects();
        flash.draw();
        scoreBoard.drawPauseLabelIfPaused(isPaused());
        screen.draw();
    }

    /*
     * Состояние игры
     */

    /** Ставит игру на паузу или снимает её. */
    public void togglePause() {
        if (status == GameStatus.OVER) return;

        status = (status == GameStatus.PAUSED) ? GameStatus.RUNNING : GameStatus.PAUSED;
    }

    /** Сообщает, стоит ли игра на паузе. */
    public boolean isPaused() {
        return status == GameStatus.PAUSED;
    }

    /** Сообщает, закончился ли раунд. */
    public boolean isRoundOver() {
        return status == GameStatus.OVER;
    }

    /** Сообщает, показан ли финальный диалог. */
    public boolean isEndingShown() {
        return endingScreen.isShown();
    }

    /*
     * Управление
     */

    /** Передаёт нажатие клавиши контроллеру. */
    @Override
    public void onKeyPress(Key key) {
        controller.handleKeyPress(key);
    }

    /** Передаёт отпускание клавиши контроллеру. */
    @Override
    public void onKeyReleased(Key key) {
        controller.handleKeyRelease(key);
    }

    /*
     * Доступ к объектам для игровых объектов и контроллера
     */

    /** Возвращает поле текущего боя. */
    public Arena getArena() {
        return arena;
    }

    /** Возвращает Марио текущего боя. */
    public Mario getMario() {
        return arena.getMario();
    }

    /** Возвращает армию врагов. */
    public EnemyArmy getEnemyArmy() {
        return arena.getEnemyArmy();
    }

    /** Возвращает кирпичи уровня. */
    public List<Brick> getBricks() {
        return arena.getBricks();
    }

    /** Возвращает вражеские пули. */
    public List<Bullet> getEnemyBullets() {
        return arena.getEnemyBullets();
    }

    /** Добавляет пулю игрока на поле. */
    public void addPlayerBullet(Bullet bullet) {
        arena.addPlayerBullet(bullet);
    }

    /** Возвращает пиксельный экран. */
    public PixelScreen getScreen() {
        return screen;
    }

    /** Возвращает вспышку экрана. */
    public ScreenFlash getFlash() {
        return flash;
    }
}
