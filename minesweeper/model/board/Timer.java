package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.Text;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.model.InteractiveObject;
import com.javarush.games.minesweeper.model.Options;

import java.util.Date;

/** Полоса таймера для режима игры на время. */
public class Timer extends InteractiveObject {
    /** Лимит времени. */
    private final float TIME_LIMIT = 500F;
    /** Цвета таймера. */
    private final Color[] COLORS = new Color[]{Color.RED, Color.DEEPPINK};
    /** Оставшееся время. */
    private float time;
    /** Время последнего тика таймера. */
    private long lastTickTime;

    /** Создаёт экземпляр Timer. */
    public Timer() {
        super();
        x = 0;
        y = 0;
        height = 1;
        width = 100;
        time = 0;
        lastTickTime = getCurrentTime();
    }

    /** Рисует объект на экране. */
    public void draw() {
        if (!Options.isTimerEnabled()) return;
        if (game.isStopped()) return;
        if (game.isFirstMove()) return;

        for (int i = x; i < ((time / TIME_LIMIT) * width); i++) {
            game.drawPixel(i, 0, COLORS[0]);
        }

        tick();
    }

    /** Обновляет таймер. */
    public void tick() {
        if (!game.isStopped() && timeIsUp()) {
            initiateLoseByTimeout();
        } else {
            countDown();
        }
    }

    /** Завершает игру по истечении времени. */
    private void initiateLoseByTimeout() {
        game.showMessage(Text.GAME_TIME_UP.getText());
        game.lose();
    }

    /** Уменьшает оставшееся время. */
    private void countDown() {
        if (!Options.isTimerEnabled()) return;
        if (getCurrentTime() - lastTickTime < 1000) return;
        time = (time > 0) ? time - Options.getDifficulty() : 0;
        swapColor();
        lastTickTime = getCurrentTime();
    }

    /** Меняет цвета таймера. */
    public void swapColor() {
        Color swap = COLORS[0];
        COLORS[0] = COLORS[1];
        COLORS[1] = swap;
    }

    /** Сбрасывает состояние в начальное. */
    public void reset() {
        if (Options.isTimerEnabled()) {
            time = TIME_LIMIT;
            lastTickTime = getCurrentTime();
        }
    }

    /** Сообщает, вышло ли время. */
    public boolean timeIsUp() {
        return (Options.isTimerEnabled() && time <= 0);
    }

    /** Возвращает бонус за оставшееся время. */
    public int getScoreBonus() {
        return (Options.isTimerEnabled()) ? ((int) (time / 50) * (Options.getDifficulty() / 5)) : 0;
    }

    /** Возвращает текущее время. */
    private long getCurrentTime() {
        return new Date().getTime();
    }
}
