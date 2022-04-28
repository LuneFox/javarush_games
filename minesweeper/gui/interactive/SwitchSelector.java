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
    private boolean enabled;
    private final Image rail;
    private final Image handle;
    private final int leftStopper;
    private final int rightStopper;
    private final String textOff;
    private final String textOn;

    public SwitchSelector(int x, int y, String textOff, String textOn) {
        super(x, y);
        this.rail = new Image(ImageType.GUI_SWITCH_RAIL, x, y + 2);
        this.handle = new Image(ImageType.GUI_SWITCH_HANDLE, x, y);
        this.width = rail.width;
        this.height = handle.height;
        this.leftStopper = x;
        this.rightStopper = x + rail.width - handle.width;
        this.textOff = textOff;
        this.textOn = textOn;
    }

    public SwitchSelector(int x, int y, String textOff, String textOn, boolean isEnabled) {
        this(x, y, textOff, textOn);
        this.enabled = isEnabled;
        handle.setPosition(enabled ? rightStopper : x, y);
    }

    @Override
    public void draw() {
        moveHandle();
        drawSwitch();
        printHelpText();
    }

    private void moveHandle() {
        if (enabled && handle.x < rightStopper) {
            handle.x++;
        } else if (!enabled && handle.x > leftStopper) {
            handle.x--;
        }
    }

    private void drawSwitch() {
        rail.draw();
        final int HANDLE_BODY_COLOR = 1;
        handle.replaceColor(enabled ? Color.GREEN : Color.RED, HANDLE_BODY_COLOR);
        handle.draw();
    }

    private void printHelpText() {
        Color helpColor = Theme.MAIN_MENU_QUOTE_FRONT.getColor();
        Printer.print(enabled ? textOn : textOff, helpColor, Display.SIZE - 2, y + height + 1, Printer.Align.RIGHT);
    }

    public void onLeftClick() {
        enabled = !enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
