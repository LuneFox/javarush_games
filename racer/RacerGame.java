package com.javarush.games.racer;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;
import com.javarush.games.racer.controller.Control;
import com.javarush.games.racer.controller.Controller;
import com.javarush.games.racer.model.car.DeLorean;
import com.javarush.games.racer.model.gameobjects.GameObject;
import com.javarush.games.racer.model.road.RoadManager;
import com.javarush.games.racer.view.Display;
import com.javarush.games.racer.view.overlay.Overlay;
import com.javarush.games.racer.view.printer.SymbolImage;

/**
 * Main game class: a 100x100 cell field on which the DeLorean races along a road that scrolls to the left.
 *
 * <p>The engine calls {@link #initialize()} once, then {@link #onTurn(int)} every 40 ms, and forwards key
 * events to {@link #onKeyPress(Key)} and {@link #onKeyReleased(Key)}. The car keeps a nearly fixed screen x
 * (the world moves instead), so the car's {@code speed} is effectively world pixels per frame, not a real
 * speed in km/h; the HUD multiplies it by 10 and labels it "МВЧ" (mph).</p>
 *
 * <p>Goal of the game: collect ten energy pickups (0.121 GW each, 1.21 GW in total) to unlock maximum speed,
 * reach 88 mph (speed &gt; 8.80) and drive into the portal. Fuel is limited, and SPACE restarts after a
 * finished race or when the tank is empty.</p>
 *
 * <p>Version 1.03.</p>
 */

public class RacerGame extends Game {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    private static final int ENDING_ANIMATION_LENGTH_IN_FRAMES = 150;
    private static final int MILLISECONDS_PER_FRAME = 40;

    private final Display display = new Display(this);
    private Controller controller;
    private DeLorean delorean;
    private RoadManager roadManager;
    private Overlay overlay;
    private int raceTime;
    private boolean isStopped;
    private int endingAnimationFrames;

    /**
     * Called once by the engine: hides the grid, sets the 100x100 screen size, injects the two static
     * dependencies, then creates the first game and shows the opening dialog.
     *
     * <p>The {@link GameObject#setGame(RacerGame)} and {@link SymbolImage#setDisplay(Display)} calls happen
     * here and nowhere else, so this static state is injected once per run and must be set before any game
     * object is created.</p>
     */
    @Override
    public void initialize() {
        showGrid(false);
        setScreenSize(WIDTH, HEIGHT);

        GameObject.setGame(this);
        SymbolImage.setDisplay(display);

        createGame();
        showOpeningDialogue();
    }

    /**
     * Creates fresh game objects and resets the per-race values. This doubles as the restart routine: it is
     * marked with {@code @Control(Key.SPACE)} for documentation, while the actual SPACE routing goes through
     * {@link #onKeyPress(Key)} and {@link Controller}.
     */
    @Control(Key.SPACE)
    public void createGame() {
        createNewGameObjects();
        resetValues();
    }

    /**
     * Shows the opening story dialog with the instructions. A {@link NullPointerException} is caught and
     * deliberately ignored: the engine throws it when dialogs are unavailable, and the game should still
     * start in that case.
     */
    private void showOpeningDialogue() {
        try {
            showMessageDialog(Color.BLACK,
                    "Святые угодники! Мы на российском шоссе 21 века, Марти!\n" +
                            "Нужно вернуться в Россию будущего, там дороги не нужны!\n" +
                            "Собери 1.21 ГВт энергии, чтобы разогнать Делореан до 88 миль в час!\n" +
                            "Главное — не попадай в лужи, а тем более в ямы!", Color.YELLOW, 15);
        } catch (NullPointerException ignored) {

        }
    }

    /**
     * Replaces all game objects with new instances: the car, the controller, the road manager and the overlay.
     * Called from {@link #createGame()} so a restart gets a fresh DeLorean and a controller bound to it.
     */
    private void createNewGameObjects() {
        delorean = new DeLorean();
        controller = new Controller(this);
        roadManager = new RoadManager(this);
        overlay = new Overlay(this);
    }

    /**
     * Resets the race counters and flags, then restarts the engine turn timer at 40 ms per turn. The objects
     * must already exist when this runs, because the first {@link #onTurn(int)} assumes they are non-null.
     */
    private void resetValues() {
        endingAnimationFrames = 0;
        raceTime = 0;
        isStopped = false;
        setTurnTimer(MILLISECONDS_PER_FRAME);
    }

    /**
     * Handles one engine turn (one frame) in this fixed order: spawn new road objects, check collisions, move
     * the car and the world, draw and flush the frame, then update the game-over flag and the timers.
     *
     * <p>The order matters. For example, {@link DeLorean#passedPortal()} can become true only after drawing
     * has moved the car into the portal, so the game-over check has to come after the drawing step.</p>
     *
     * @param step the engine's turn counter (unused by this game)
     */
    @Override
    public void onTurn(int step) {
        roadManager.generateNewRoadObjects(delorean);
        roadManager.checkOverlapsWithCar(delorean);
        moveAll();
        drawAll();
        checkGameOver();
        countTime();
        countEndingAnimationFrames();
    }

    /**
     * Moves the car and scrolls the road objects to the left by the car's current speed (world pixels per
     * frame). This is what creates the illusion of motion while the car itself barely changes its x.
     */
    private void moveAll() {
        delorean.move();
        roadManager.moveObjects(delorean.getSpeed());
    }

    /**
     * Draws the road, the car and the overlay into the off-screen pixel buffer, then flushes that buffer to the
     * engine with {@link Display#draw()}. This is the only place where the frame is flushed, once per turn.
     */
    private void drawAll() {
        roadManager.drawObjects();
        delorean.draw();
        overlay.draw();
        display.draw();
    }

    /**
     * Ends the race once the car has driven past the portal: stops the car and sets the stopped flag, which
     * freezes the race timer and starts the ending animation. This is the only place that sets
     * {@code isStopped}.
     */
    private void checkGameOver() {
        if (delorean.passedPortal()) {
            delorean.stop();
            isStopped = true;
        }
    }

    /**
     * Adds one frame (40 ms) to the race time unless the race is stopped or the car has not covered any
     * distance yet ({@code distance <= 0}). Because the distance counter never decreases, the clock starts at
     * the first movement but does not pause if the car later comes to a standstill. It therefore measures
     * elapsed game frames, not wall-clock time.
     */
    private void countTime() {
        if (isStopped) return;
        if (delorean.getDistance() <= 0) return;

        raceTime += MILLISECONDS_PER_FRAME;
    }

    /**
     * Advances the ending-animation counter by one frame per turn, but only after the car has stopped and as
     * long as the animation is not finished. The overlay uses this counter for its stepped reveal at 50, 80,
     * 100, 120 and 150 frames.
     */
    private void countEndingAnimationFrames() {
        if (!isStopped) return;
        if (isEndingAnimationFinished()) return;

        endingAnimationFrames++;
    }

    /**
     * Tells whether the ending animation has completed. It becomes true at 151 frames, one frame past the last
     * overlay step and past {@code ENDING_ANIMATION_LENGTH_IN_FRAMES} (150).
     *
     * @return true when the ending animation has finished
     */
    public boolean isEndingAnimationFinished() {
        return endingAnimationFrames > ENDING_ANIMATION_LENGTH_IN_FRAMES;
    }

    /**
     * Tells whether the car has collected all ten energy pickups. The road manager calls this delegate through
     * the game to stop spawning new energy once 1.21 GW has been reached.
     *
     * @return true when the DeLorean holds maximum energy
     */
    public boolean deloreanHasMaxEnergy() {

        return delorean.hasMaxEnergy();
    }


    /*
     * Controls
     */

    /**
     * Routes a key-down event to the controller, which forwards it to the active control strategy. The
     * {@code @Control} annotations are documentation only; this engine callback is where key handling really
     * starts.
     *
     * @param key the key that was pressed
     */
    @Override
    public void onKeyPress(Key key) {
        controller.pressKey(key);
    }

    /**
     * Routes a key-up event to the controller so that a direction can be cleared when its key is released.
     *
     * @param key the key that was released
     */
    @Override
    public void onKeyReleased(Key key) {
        controller.releaseKey(key);
    }


    /*
     * Getters, setters
     */

    /**
     * Returns the display buffer used for all drawing. It is created once with the game and survives restarts.
     *
     * @return the game's display
     */
    public Display getDisplay() {
        return display;
    }

    /**
     * Returns the car of the current race. The instance is replaced by every {@link #createGame()} call.
     *
     * @return the current DeLorean
     */
    public DeLorean getDelorean() {
        return delorean;
    }

    /**
     * Returns the accumulated race time in milliseconds of game time, not wall-clock time.
     *
     * @return the race time in milliseconds
     */
    public int getRaceTime() {
        return raceTime;
    }

    /**
     * Tells whether the race is over, that is, whether the car has passed the portal and been stopped. The
     * ending animation and the SPACE restart both depend on this flag.
     *
     * @return true when the car has finished the race
     */
    public boolean isStopped() {
        return isStopped;
    }

    /**
     * Returns how many ending-animation frames have elapsed so far. The overlay uses it to reveal the result
     * screen step by step.
     *
     * @return frames counted since the car stopped, or 0 while it is still driving
     */
    public int getEndingAnimationFrames() {
        return endingAnimationFrames;
    }
}
