package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.ShopItem;
import com.javarush.games.minesweeper.Strings;
import com.javarush.games.minesweeper.view.graphics.*;

/**
 * Shows item help when you right-click an item in the shop.
 */

public final class ViewItemHelp extends View {
    private ShopItem displayItem;

    public ViewItemHelp() {
        this.screen = Screen.ITEM_HELP;
    }

    public void update() {
        super.update();
        Strings.generateNewShieldDescription();
        IMAGES_CACHE.get(VisualElement.WIN_MENU).draw();
        displayItem.icon.setPosition(5, 5);
        displayItem.icon.draw();
        if (displayItem.id == ShopItem.ID.SHIELD) {
            displayItem.description = Strings.generateNewShieldDescription().toString();
        }
        Printer.print("[" + displayItem.name + "]", Color.YELLOW, 25, 9);
        Printer.print(displayItem.description, 4, 25);
        BUTTONS_CACHE.get(Button.ButtonID.CONFIRM).draw();
    }

    public void setDisplayItem(ShopItem item) {
        this.displayItem = item;
    }
}
