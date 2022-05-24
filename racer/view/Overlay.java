package com.javarush.games.racer.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.model.decor.Marty;
import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.view.printer.Printer;
import com.javarush.games.racer.view.printer.TextAlign;

public class Overlay {
    private final RacerGame game;
    private final GameObject energyIcon;
    private final GameObject speedometerIcon;
    private final GameObject gasIcon;
    private final GameObject gasMeterBackground;
    private final GameObject gasMeter;
    public Marty marty;

    public Overlay(RacerGame game) {
        this.game = game;

        energyIcon = new GameObject(93, 1);
        energyIcon.setStaticView(Shapes.ENERGY_ICON);

        speedometerIcon = new GameObject(2, 1);
        speedometerIcon.setStaticView(Shapes.SPEEDOMETER_ICON);

        gasIcon = new GameObject(2, 91);
        gasIcon.setStaticView(Shapes.GAS_ICON);

        gasMeterBackground = new GameObject(11, 93);
        gasMeterBackground.setStaticView(Shapes.GAS_METER_BACKGROUND);

        gasMeter = new GameObject(15, 94);
        gasMeter.setStaticView(Shapes.GAS_METER);

        marty = new Marty();
    }

    public void draw() {
        printSpeed();
        printEnergy();
        printGas();
        drawEnding();
    }

    private void printSpeed() {
        int displaySpeed = game.isStopped ? 88 : (int) (game.delorean.getSpeed() * 10);

        Printer.print("<" + displaySpeed + " МВЧ>", Color.WHITE, 11, 0);
        speedometerIcon.draw();
    }

    private void printEnergy() {
        Printer.print("<" + game.delorean.getEnergy() + " ГВТ>", Color.LAWNGREEN, RacerGame.WIDTH - 8, 0, TextAlign.RIGHT);
        energyIcon.draw();
    }

    private void printGas() {
        double gas = game.delorean.getGas();

        gasIcon.draw();
        gasMeterBackground.draw();
        gasMeter.draw(gasMeter.x + gas, gasMeter.y);

        if (gas == 0) {
            Printer.print("<НЕТ БЕНЗИНА!>", Color.PINK, 46, 91);
            if (game.delorean.getSpeed() != 0) return;

            Printer.print("ПРОБЕЛ - НАЧАТЬ ЗАНОВО", Color.LAWNGREEN, 0, 80, TextAlign.CENTER);
        }
    }

    private void drawEnding() {
        if (!game.isStopped) return;

        if (game.framesAfterStop <= RacerGame.ENDING_ANIMATION_LENGTH) {
            game.framesAfterStop++;
        }

        if (game.framesAfterStop < 50) return;

        marty.draw();
        if (game.framesAfterStop < 80) return;

        printEndingResultTime();
        if (game.framesAfterStop < 100) return;

        printEndingUsedGas();
        if (game.framesAfterStop < 120) return;

        printEndingDistance();
        if (game.framesAfterStop < 150) return;

        printEndingPressSpaceToTryAgain();
    }

    private void printEndingResultTime() {
        final int raceTimeSeconds = (game.raceTime / 1000);
        final int raceTimeHundredths = (game.raceTime % 1000) / 10;

        Printer.print("ВРЕМЯ: " + raceTimeSeconds + "' " + raceTimeHundredths + "\"", Color.LAWNGREEN, 0, 12, TextAlign.CENTER);
    }

    private void printEndingUsedGas() {
        final double usedGas = roundDouble(game.delorean.getUsedGasInLitres());
        Printer.print("ИСП. БЕНЗИН: " + usedGas + " Л.", Color.LAWNGREEN, 0, 21, TextAlign.CENTER);
    }

    private double roundDouble(double value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private void printEndingDistance() {
        long distance = (long) game.delorean.getDistance();
        Printer.print("ДИСТАНЦИЯ: " + distance + " М.", Color.LAWNGREEN, 0, 30, TextAlign.CENTER);
    }

    private void printEndingPressSpaceToTryAgain() {
        Printer.print("ПРОБЕЛ - СНОВА", Color.LAWNGREEN, 0, 80, TextAlign.CENTER);
    }
}
