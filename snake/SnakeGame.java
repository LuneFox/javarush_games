package com.javarush.games.snake;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.snake.controller.Controller;
import com.javarush.games.snake.controller.strategies.GameFieldControlStrategy;
import com.javarush.games.snake.model.Phase;
import com.javarush.games.snake.model.Score;
import com.javarush.games.snake.model.Snake;
import com.javarush.games.snake.model.Strings;
import com.javarush.games.snake.model.map.Coordinate;
import com.javarush.games.snake.model.map.Stage;
import com.javarush.games.snake.model.map.StageManager;
import com.javarush.games.snake.view.impl.GameFieldView;

import java.util.Date;

/** Главный класс игры: состояние, ход, таймер и ввод. */
public class SnakeGame extends Game {
    public static final int SIZE = 32;
    public static final int HUD_ROWS = 4;
    public static final int PLAYFIELD_MIN_X = 0;
    public static final int PLAYFIELD_MIN_Y = HUD_ROWS;
    public static final int PLAYFIELD_MAX_X = SIZE - 1;
    public static final int PLAYFIELD_MAX_Y = SIZE - 1;

    private static final int MAX_TURN_DELAY = 300;
    private static final int MIN_TURN_DELAY = 100;
    private static final int TURN_DELAY_STEP = 10;
    private static final int MAX_ORB_BONUS = 300;
    private static final int GAME_OVER_TEXT_SIZE = 27;

    private static SnakeGame instance;

    private final Score score = new Score();

    private Controller controller;
    private Snake snake;
    private Stage stage;
    private Strings gameOverReason;
    private int turnDelay;
    private boolean isStopped;
    private boolean isPaused;

    /** Возвращает единственный экземпляр игры. */
    public static SnakeGame getInstance() {
        return instance;
    }

    /** Настраивает экран, контроллер и стартовое меню. */
    public void initialize() {
        // Другие классы игры сохраняют getInstance() в статических полях,
        // поэтому экземпляр нужно опубликовать до их загрузки.
        instance = this;
        controller = new Controller();

        showGrid(false);
        setScreenSize(SIZE, SIZE);
        loadStage();
        Phase.proceed(Phase.MAIN_MENU);
    }

    /** Начинает новую игру на текущем уровне. */
    public final void createGame() {
        loadStage();
        createSnake();
        resetGameState();
        setTurnTimer(turnDelay);
    }

    /** Загружает текущий уровень из менеджера уровней. */
    public void loadStage() {
        stage = StageManager.getCurrentStage();
    }

    /** Создаёт змейку в стартовой точке уровня. */
    private void createSnake() {
        final Coordinate start = stage.getSnakeStartPlace();

        snake = new Snake(this, start.x, start.y, stage.getSnakeStartDirection());
    }

    /** Сбрасывает счёт, уровень и флаги перед игрой. */
    private void resetGameState() {
        score.reset();
        stage.initialize();
        gameOverReason = null;
        turnDelay = MAX_TURN_DELAY;
        isStopped = false;
        isPaused = false;
        GameFieldControlStrategy.getInstance().reset();
    }

    /** Обрабатывает один тик игрового таймера. */
    public void onTurn(int step) {
        if (isStopped || isPaused) return;

        if (!stage.isStarted()) {
            stage.showBriefingMessage();
            return;
        }

        performTurn();

        if (isStopped) return;

        setTurnTimer(turnDelay);
        Phase.proceed(Phase.GAME_FIELD);
    }

    /** Выполняет ход змейки и обновление мира. */
    private void performTurn() {
        snake.move();

        // Мёртвая змея не должна есть орбы и менять мир.
        if (!snake.isAlive()) {
            checkGameState();
            return;
        }

        stage.collectOrbs(snake);
        applyPassiveTerrainEffects();
        // Пассивные эффекты могли изменить клетки под телом змейки.
        snake.checkTerrainContacts();
        checkGameState();
    }

    /** Применяет пассивные эффекты всех клеток поля. */
    private void applyPassiveTerrainEffects() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                stage.getTerrain(x, y).applyPassiveEffect();
            }
        }
    }

    /** Проверяет смерть змейки и прохождение уровня. */
    private void checkGameState() {
        if (!snake.isAlive()) {
            gameOver();
        } else if (stage.isCompleted()) {
            win();
        }
    }

    /** Останавливает игру и показывает причину смерти. */
    private void gameOver() {
        isStopped = true;
        stopTurnTimer();
        Phase.proceed(Phase.GAME_FIELD);

        final Strings reason = gameOverReason == null ? Strings.GAME_OVER_UNKNOWN : gameOverReason;
        showMessageDialog(Color.YELLOW, reason.get(), Color.RED, GAME_OVER_TEXT_SIZE);
    }

    /** Останавливает игру, показывает прохождение и берёт следующий уровень. */
    private void win() {
        isStopped = true;
        stopTurnTimer();
        Phase.proceed(Phase.GAME_FIELD);
        stage.showCompleteMessage();
        StageManager.selectNextStage();
    }

    /** Восстанавливает обычную задержку хода. */
    public void applyNormalTurnDelay() {
        this.turnDelay = getNormalTurnDelay();
    }

    /** Вычисляет обычную задержку по длине змейки. */
    public int getNormalTurnDelay() {
        int delay = MAX_TURN_DELAY - snake.getLength() * TURN_DELAY_STEP;
        return Math.max(delay, MIN_TURN_DELAY);
    }

    /** Ставит игру на паузу или снимает её. */
    public void togglePause() {
        isPaused = !isPaused;

        if (isPaused) {
            stopTurnTimer();
            GameFieldView.getInstance().drawSleepingLabel();
        } else {
            setTurnTimer(turnDelay);
        }
    }

    /** Считает бонус за орб по времени с начала уровня. */
    public long calculateOrbBonus() {
        long secondsInStage = (currentTimeMillis() - stage.getStartTimeMillis()) / 1000;
        return Math.max(MAX_ORB_BONUS - secondsInStage, 0);
    }

    /** Проверяет, находится ли клетка вне игрового поля. */
    public static boolean isOutOfBounds(int x, int y) {
        return x < PLAYFIELD_MIN_X || x > PLAYFIELD_MAX_X
                || y < PLAYFIELD_MIN_Y || y > PLAYFIELD_MAX_Y;
    }

    /** Возвращает текущее время в миллисекундах. */
    private static long currentTimeMillis() {
        return new Date().getTime();
    }

    /** Остановлена ли игра. */
    public boolean isStopped() {
        return isStopped;
    }

    /** Поставлена ли игра на паузу. */
    public boolean isPaused() {
        return isPaused;
    }

    /** Возвращает змейку. */
    public Snake getSnake() {
        return snake;
    }

    /** Возвращает счёт текущей игры. */
    public Score getScore() {
        return score;
    }

    /** Задаёт задержку между ходами. */
    public void setTurnDelay(int turnDelay) {
        this.turnDelay = turnDelay;
    }

    /** Запоминает причину смерти для финального сообщения. */
    public void setGameOverReason(Strings reason) {
        this.gameOverReason = reason;
    }

    /** Подменяет текущий уровень (используется редактором). */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /** Возвращает текущий уровень. */
    public Stage getStage() {
        return stage;
    }

    /* Управление */

    /** Передаёт нажатие клавиши контроллеру. */
    @Override
    public void onKeyPress(Key key) {
        controller.pressKey(key);
    }

    /** Передаёт отпускание клавиши контроллеру. */
    @Override
    public void onKeyReleased(Key key) {
        controller.releaseKey(key);
    }

    /** Передаёт левый клик контроллеру. */
    @Override
    public void onMouseLeftClick(int x, int y) {
        controller.leftClick(x, y);
    }

    /** Передаёт правый клик контроллеру. */
    @Override
    public void onMouseRightClick(int x, int y) {
        controller.rightClick(x, y);
    }
}
