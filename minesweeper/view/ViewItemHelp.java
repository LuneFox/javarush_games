package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.ShopItem;
import com.javarush.games.minesweeper.Strings;
import com.javarush.games.minesweeper.graphics.VisualElement;
import com.javarush.games.minesweeper.graphics.Button;

/**
 * Shows item help when you right-click an item in the shop.
 */

public final class ViewItemHelp extends View {
    private ShopItem currentItem;

    public ViewItemHelp(MinesweeperGame game) {
        this.game = game;
        this.screen = Screen.ITEM_HELP;
    }

    @Override
    public void display() {
        display(currentItem);
    }

    public void display(ShopItem item) {
        currentItem = item;
        super.display();
        Strings.generateNewShieldDescription();
        IMAGES_CACHE.get(VisualElement.WIN_MENU).draw();
        item.icon.setPosition(5, 5);
        item.icon.draw();
        if (item.id == ShopItem.ID.SHIELD) {
            item.description = Strings.generateNewShieldDescription().toString();
        }
        game.print("[" + item.name + "]", Color.YELLOW, 25, 9);
        game.print(item.description, 4, 25);
        BUTTONS_CACHE.get(Button.ButtonID.CONFIRM).draw();
    }
}
