package com.javarush.games.racer.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.view.printer.Printer;
import com.javarush.games.racer.view.printer.TextAlign;

public class Overlay {
    private final RacerGame game;
    private final GameObject energyIcon;
    private final GameObject speedometerIcon;

    public Overlay(RacerGame game) {
        this.game = game;

        energyIcon = new GameObject(93, 1);
        energyIcon.setStaticView(Shapes.ENERGY_ICON);

        speedometerIcon = new GameObject(1, 1);
        speedometerIcon.setStaticView(Shapes.SPEEDOMETER_ICON);
    }

    public void draw() {
        printSpeed();
        printEnergy();
        drawEnding();
    }

    private void printSpeed() {
        int displaySpeed = game.isStopped ? 88 : (int) (game.delorean.getSpeed() * 10);

        Printer.print("<" + displaySpeed + " МВЧ>", Color.WHITE, 10, 0);
        speedometerIcon.draw();
    }

    private void printEnergy() {
        Printer.print("<" + game.delorean.getEnergy() + " ГВТ>", Color.LAWNGREEN, RacerGame.WIDTH - 8, 0, TextAlign.RIGHT);
        energyIcon.draw();
    }

    private void drawEnding() {
        if (!game.isStopped) return;

        if (game.framesAfterStop <= RacerGame.ENDING_ANIMATION_LENGTH) {
            game.framesAfterStop++;
        }

        if (game.framesAfterStop < 50) return;

        game.marty.draw();
        if (game.framesAfterStop < 80) return;

        printResultTime();
    }

    private void printResultTime() {
        final int raceTimeSeconds = (game.raceTime / 1000);
        final int raceTimeHundredths = (game.raceTime % 1000) / 10;

        Printer.print("<ВРЕМЯ: " + raceTimeSeconds + "' " + raceTimeHundredths + "\">", Color.WHITE, 0, 12, TextAlign.CENTER);
    }
}
