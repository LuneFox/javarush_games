package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.model.Screen;
import com.javarush.games.minesweeper.model.shop.ShopItem;
import com.javarush.games.minesweeper.model.Strings;
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
        Strings.generateNewShieldDescription();
        Cache.get(VisualElement.WIN_MENU).draw();
        displayItem.icon.setPosition(5, 10);
        displayItem.icon.draw();
        if (displayItem.id == ShopItem.ID.SHIELD) {
            displayItem.description = Strings.generateNewShieldDescription().toString();
        }
        Printer.print("[" + displayItem.name + "]", Color.YELLOW, 25, 14);
        Printer.print(displayItem.description, 4, 30);
        Cache.get(Button.ButtonID.GENERAL_CLOSE).draw();
        super.update();
    }

    public void setDisplayItem(ShopItem item) {
        this.displayItem = item;
    }
}
