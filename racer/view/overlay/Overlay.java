package com.javarush.games.racer.view.overlay;

import com.javarush.engine.cell.Color;
import com.javarush.games.racer.NumberUtil;
import com.javarush.games.racer.RacerGame;
import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.view.Shapes;
import com.javarush.games.racer.view.printer.Printer;
import com.javarush.games.racer.view.printer.TextAlign;

/**
 * Draws the HUD on top of the road and the DeLorean: the speed and energy readouts, the gas gauge,
 * and the results revealed during the ending animation. The icons are game objects with static views
 * placed at fixed cells, and all text is rendered by the pixel-font {@link Printer}.
 */
public class Overlay {
    private final RacerGame game;
    private final DeLorean delorean;
    private final GameObject energyIcon;
    private final GameObject speedometerIcon;
    private final GameObject gasIcon;
    private final GameObject gasMeterBackground;
    private final GameObject gasMeter;
    private final Marty marty;

    /**
     * Caches the game and its DeLorean, then creates the fixed HUD icons (speedometer, energy, gas
     * icon, gauge background and gauge bar) and the Marty sprite. Requires the static
     * {@code GameObject.setGame} and display wiring to have been done already, because
     * {@code setStaticView} resolves its sprite through them.
     *
     * @param game running game whose DeLorean supplies the displayed values
     */
    public Overlay(RacerGame game) {
        this.game = game;
        this.delorean = game.getDelorean();

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

    /**
     * Draws the whole HUD for the current frame and advances the ending reveal according to the
     * game's ending-animation frame counter.
     */
    public void draw() {
        printSpeed();
        printEnergy();
        printGas();
        drawEnding(game.getEndingAnimationFrames());
    }

    /**
     * Prints the speed as the DeLorean's internal speed times ten, so the portal speed 8.80 shows as
     * 88; while the game is stopped it deliberately shows the iconic 88 anyway. The white
     * {@code <... МВЧ>} text wraps the value in outline markup, and the speedometer icon is drawn at
     * the top-left.
     */
    private void printSpeed() {
        final int displaySpeed = game.isStopped() ? 88 : (int) (delorean.getSpeed() * 10);

        Printer.print("<" + displaySpeed + " МВЧ>", Color.WHITE, 11, 0);
        speedometerIcon.draw();
    }

    /**
     * Prints the energy in gigawatts, rounded to two decimals, right-aligned near the right screen
     * edge, and draws the energy icon at the top-right. The caret base is column
     * {@code RacerGame.WIDTH - 8}, so right alignment moves the text leftwards from there.
     */
    private void printEnergy() {
        final double energy = NumberUtil.roundDouble(delorean.getEnergy());
        Printer.print("<" + energy + " ГВТ>", Color.LAWNGREEN, RacerGame.WIDTH - 8, 0, TextAlign.RIGHT);
        energyIcon.draw();
    }

    /**
     * Draws the gas icon and gauge, then draws the one-pixel-wide bar at
     * {@code gasMeter.x + gas}, so the remaining fuel in litres shifts the bar right over the fixed
     * background and empties the gauge as it decreases. When {@code gas == 0} exactly it prints the
     * no-fuel warning, and only once the car has fully stopped does it also offer the restart
     * prompt.
     */
    private void printGas() {
        final double gas = delorean.getGas();

        gasIcon.draw();
        gasMeterBackground.draw();
        gasMeter.draw(gasMeter.x + gas, gasMeter.y);

        if (gas == 0) {
            Printer.print("<НЕТ БЕНЗИНА!>", Color.PINK, 46, 91);
            if (delorean.getSpeed() != 0) return;

            Printer.print("ПРОБЕЛ - НАЧАТЬ ЗАНОВО", Color.LAWNGREEN, 0, 80, TextAlign.CENTER);
        }
    }

    /**
     * Reveals the ending one step at a time: Marty at 50 frames, the race time at 80, the used gas
     * at 100, the distance at 120, and the restart prompt at 150. Each threshold returns early, so
     * earlier results stay visible once they have appeared.
     *
     * @param frames frames elapsed since the DeLorean stopped, taken from the game's ending counter
     */
    private void drawEnding(int frames) {
        if (frames < 50) return;
        marty.draw();
        if (frames < 80) return;
        printEndingResultTime();
        if (frames < 100) return;
        printEndingUsedGas();
        if (frames < 120) return;
        printEndingDistance();
        if (frames < 150) return;
        printEndingPressSpaceToTryAgain();
    }

    /**
     * Prints the race time as seconds and hundredths of a second, centered at row 12.
     */
    private void printEndingResultTime() {
        final int raceTimeSeconds = (game.getRaceTime() / 1000);
        final int raceTimeHundredths = (game.getRaceTime() % 1000) / 10;

        Printer.print("ВРЕМЯ: " + raceTimeSeconds + "' " + raceTimeHundredths + "\"", Color.LAWNGREEN, 0, 12, TextAlign.CENTER);
    }

    /**
     * Prints the fuel consumed during the race in litres, rounded to two decimals and centered at
     * row 21.
     */
    private void printEndingUsedGas() {
        final double usedGas = NumberUtil.roundDouble(delorean.getUsedGasInLitres());
        Printer.print("ИСП. БЕНЗИН: " + usedGas + " Л.", Color.LAWNGREEN, 0, 21, TextAlign.CENTER);
    }

    /**
     * Prints the distance travelled in metres, centered at row 30; the cast to long truncates the
     * fractional part instead of rounding it.
     */
    private void printEndingDistance() {
        final long distance = (long) delorean.getDistance();
        Printer.print("ДИСТАНЦИЯ: " + distance + " М.", Color.LAWNGREEN, 0, 30, TextAlign.CENTER);
    }

    /**
     * Prints the final "press space to play again" prompt, centered at row 80.
     */
    private void printEndingPressSpaceToTryAgain() {
        Printer.print("ПРОБЕЛ - СНОВА", Color.LAWNGREEN, 0, 80, TextAlign.CENTER);
    }
}
