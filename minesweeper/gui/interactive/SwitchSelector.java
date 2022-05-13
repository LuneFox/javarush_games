package com.javarush.games.minesweeper.gui.interactive;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Display;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.InteractiveObject;

/**
 * A fidget that can be turned on and off.
 * Has a form of a common switch with a handle that slides along the rail.
 */

public class SwitchSelector extends InteractiveObject {
    private State state;
    private final Image rail;
    private final Image handle;
    private final int leftStopper;
    private final int rightStopper;
    private final String labelOff;
    private final String labelOn;

    public enum State {
        ON, OFF
    }

    public SwitchSelector(int x, int y, String labelOff, String labelOn) {
        super(x, y);

        this.rail = new Image(ImageType.GUI_SWITCH_RAIL, x, y + 2);
        this.handle = new Image(ImageType.GUI_SWITCH_HANDLE, x, y);

        this.width = rail.width;
        this.height = handle.height;

        this.leftStopper = x;
        this.rightStopper = x + rail.width - handle.width;

        this.labelOff = labelOff;
        this.labelOn = labelOn;

        this.state = State.OFF;
    }

    public SwitchSelector(int x, int y, String labelOff, String labelOn, State state) {
        this(x, y, labelOff, labelOn);
        this.state = state;

        if (state == State.ON) {
            handle.setPosition(rightStopper, y);
        }
    }

    @Override
    public void draw() {
        moveHandle();
        drawSwitch();
        printLabel();
    }

    private void moveHandle() {
        if (state == State.ON && handle.x < rightStopper) {
            handle.x++;
        } else if (state == State.OFF && handle.x > leftStopper) {
            handle.x--;
        }
    }

    private void drawSwitch() {
        rail.draw();
        final int HANDLE_BODY_COLOR = 1;
        handle.changeColor(state == State.ON ? Color.GREEN : Color.RED, HANDLE_BODY_COLOR);
        handle.draw();
    }

    private void printLabel() {
        String label = selectLabel();
        Color textColor = Theme.MAIN_MENU_QUOTE_FRONT.getColor();
        Printer.print(label, textColor, (Display.SIZE - 2), (y + height + 1), Printer.Align.RIGHT);
    }

    private String selectLabel() {
        return state == State.ON ? labelOn : labelOff;
    }

    public void onLeftClick() {
        if (state == State.ON) state = State.OFF;
        else state = State.ON;
    }

    public boolean isEnabled() {
        return state == State.ON;
    }
}
