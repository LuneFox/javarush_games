package com.javarush.games.ticktacktoe.model;

import com.javarush.engine.cell.Color;
import com.javarush.games.ticktacktoe.TicTacToeGame;
import com.javarush.games.ticktacktoe.model.gameobjects.Field;
import com.javarush.games.ticktacktoe.view.printer.Printer;
import com.javarush.games.ticktacktoe.view.printer.TextAlign;

public class Computer {

    private final TicTacToeGame game;

    private long thinkingTime;
    private long thinkingSpeed;
    private long speedMessageShowDelay;

    public Computer(TicTacToeGame game, long thinkingSpeed) {
        this.game = game;
        this.thinkingSpeed = thinkingSpeed;
    }

    public void increaseSpeed() {
        speedMessageShowDelay = 20;

        if (thinkingSpeed <= 1) return;
        thinkingSpeed--;
    }

    public void decreaseSpeed() {
        speedMessageShowDelay = 20;

        if (thinkingSpeed >= 10) return;
        thinkingSpeed++;
    }

    public void takeTurn() {
        Field field = game.getField();

        if (!isThinking(thinkingSpeed)) {
            field.doAutomaticTurn();
            resetThinkingTime();
        }

        if (field.noMovesLeft()) {
            game.setComputerTurn(false);
        }
    }

    private boolean isThinking(long speed) {
        printThinkingDots(speed);

        if (thinkingTime < speed * 8) {
            thinkingTime++;
            return true;
        }

        return false;
    }

    private void printThinkingDots(long speed) {
        if (thinkingTime < speed) Printer.print(" ", Color.YELLOW, 50, 89, TextAlign.CENTER);
        else if (thinkingTime < speed * 3) Printer.print(".", Color.YELLOW, 50, 89, TextAlign.CENTER);
        else if (thinkingTime < speed * 5) Printer.print(". .", Color.YELLOW, 50, 89, TextAlign.CENTER);
        else if (thinkingTime < speed * 7) Printer.print(". . .", Color.YELLOW, 50, 89, TextAlign.CENTER);
    }

    private void resetThinkingTime() {
        thinkingTime = 0;
    }

    public boolean showSpeedSetting() {
        if (speedMessageShowDelay > 0) {
            Printer.print("СКОРОСТЬ: " + getSpeedForDisplay(), Color.LAWNGREEN, 1, 91, TextAlign.CENTER);
            speedMessageShowDelay--;
            return true;
        }
        return false;
    }

    private long getSpeedForDisplay() {
        return 11 - thinkingSpeed;
    }
}
