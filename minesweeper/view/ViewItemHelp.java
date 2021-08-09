package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.Screen;
import com.javarush.games.minesweeper.ShopItem;
import com.javarush.games.minesweeper.Strings;
import com.javarush.games.minesweeper.graphics.Bitmap;
import com.javarush.games.minesweeper.graphics.Button;

/**
 * Shows item help when you right-click an item in the shop.
 */

public final class ViewItemHelp extends View {
    public ViewItemHelp(MinesweeperGame game) {
        this.game = game;
        this.screenType = Screen.ScreenType.ITEM_HELP;
    }

    @Override
    public void display() {

    }

    public void display(ShopItem item){
        super.display();
        Strings.generateNewShieldDescription();
        IMAGES.get(Bitmap.WINDOW_ITEM_HELP).draw();
        item.icon.setPosition(5, 5);
        item.icon.draw();
        if (item.id == ShopItem.ID.SHIELD) {
            item.description = Strings.generateNewShieldDescription().toString();
        }
        game.print(item.name, Color.YELLOW, 25, 9);
        game.print(item.description, 4, 25);
        BUTTONS.get(Button.ButtonID.CONFIRM).draw();
    }
}
