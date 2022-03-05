package com.javarush.games.minesweeper.model.options;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.model.DrawableObject;
import com.javarush.games.minesweeper.view.graphics.Image;
import com.javarush.games.minesweeper.view.graphics.Printer;
import com.javarush.games.minesweeper.view.graphics.Theme;
import com.javarush.games.minesweeper.view.graphics.VisualElement;

/**
 * A fidget that can be turned on and off.
 * Has a form of a common switch with a handle that slides along the rail.
 */

public class SwitchSelector extends DrawableObject {
    private boolean enabled;
    private final Image rail;
    private final Image handle;
    private final int leftStopper;
    private final int rightStopper;
    private final String textOff;
    private final String textOn;

    public SwitchSelector(int x, int y, String textOff, String textOn) {
        super(x, y);
        this.enabled = false;
        this.rail = new Image(VisualElement.MENU_SWITCH_RAIL, x, y + 2);
        this.handle = new Image(VisualElement.MENU_SWITCH, x, y);
        this.width = rail.width;
        this.height = handle.height;
        this.leftStopper = x;
        this.rightStopper = x + rail.width - handle.width;
        this.textOff = textOff;
        this.textOn = textOn;
    }

    @Override
    public void draw() {
        // Move handle
        if (enabled && handle.x < rightStopper)
            handle.x++;
        else if (!enabled && handle.x > leftStopper)
            handle.x--;

        // Draw switch
        handle.replaceColor(enabled ? Color.GREEN : Color.RED, 1);
        rail.draw();
        handle.draw();

        // Write help text
        Color helpColor = Theme.MAIN_MENU_QUOTE_FRONT.getColor();
        Printer.print(enabled ? textOn : textOff, helpColor, rightStopper, y + height + 1, true);
    }

    protected void onTouch() {
        enabled = !enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}