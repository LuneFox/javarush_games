package com.javarush.games.ticktacktoe.model;

import com.javarush.engine.cell.Color;
import com.javarush.games.ticktacktoe.TicTacToeGame;
import com.javarush.games.ticktacktoe.model.gameobjects.field.Field;
import com.javarush.games.ticktacktoe.view.printer.Printer;
import com.javarush.games.ticktacktoe.view.printer.TextAlign;

/**
 * Виртуальный соперник для игры
 *
 * @author LuneFox
 */
public class Computer {

    /** Экземпляр игры */
    private final TicTacToeGame game;
    /** Количество кадров с тех пор, как компьютер начал думать */
    private long totalThinkingTime;
    /** Задержка перед действием - чем больше, тем дольше думает компьютер */
    private long thinkingDelay;
    /** Количество кадров, в течение которых отображается настройка скорости компьютера */
    private long speedMessageShowDelay;

    public Computer(TicTacToeGame game) {
        this.game = game;
        this.thinkingDelay = 5;
    }

    /**
     * Увеличение скорости действий (уменьшение задержки)
     */
    public void increaseSpeed() {
        speedMessageShowDelay = 20;
        if (thinkingDelay <= 1) return;
        thinkingDelay--;
    }

    /**
     * Уменьшение скорости действий (увеличение задержки)
     */
    public void decreaseSpeed() {
        speedMessageShowDelay = 20;
        if (thinkingDelay >= 10) return;
        thinkingDelay++;
    }

    /**
     * Совершение хода в игре
     */
    public void takeTurn() {
        Field field = game.getField();
        if (!isThinking()) {
            game.getManager().doAutomaticTurn();
            resetThinkingTime();
        }
        if (field.noMovesLeft()) {
            game.setComputerTurn(false);
        }
    }

    /**
     * Проверка, думает ли сейчас компьютер над ходом
     *
     * @return пока ещё думает
     */
    private boolean isThinking() {
        printThinkingDots();
        if (totalThinkingTime < thinkingDelay * 8) {
            totalThinkingTime++;
            return true;
        }
        return false;
    }

    /**
     * Вывод на экран точек во время размышления
     */
    private void printThinkingDots() {
        if (totalThinkingTime < thinkingDelay) Printer.print(" ", Color.YELLOW, 50, 89, TextAlign.CENTER);
        else if (totalThinkingTime < thinkingDelay * 3) Printer.print(".", Color.YELLOW, 50, 89, TextAlign.CENTER);
        else if (totalThinkingTime < thinkingDelay * 5) Printer.print(". .", Color.YELLOW, 50, 89, TextAlign.CENTER);
        else if (totalThinkingTime < thinkingDelay * 7) Printer.print(". . .", Color.YELLOW, 50, 89, TextAlign.CENTER);
    }

    /**
     * Сброс времени размышления
     */
    private void resetThinkingTime() {
        totalThinkingTime = 0;
    }

    /**
     * Отображение настройки скорости
     */
    public boolean showSpeedSetting() {
        if (speedMessageShowDelay > 0) {
            Printer.print(Message.COMPUTER_SPEED + getSpeed(), Color.LAWNGREEN, 1, 91, TextAlign.CENTER);
            speedMessageShowDelay--;
            return true;
        }
        return false;
    }

    /**
     * Обращает значение задержки действий в понятное значение скорости
     *
     * @return скорость размышления компьютера
     */
    private long getSpeed() {
        return 11 - thinkingDelay;
    }
}
