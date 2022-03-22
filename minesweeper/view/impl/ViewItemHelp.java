package com.javarush.games.minesweeper.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.Strings;
import com.javarush.games.minesweeper.model.shop.ShopItem;
import com.javarush.games.minesweeper.view.View;

public class ViewItemHelp extends View {

    private final Button closeButton = new Button(88, 2, 0, 0, "x", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.SHOP);
        }
    };
    Image background = Image.cache.get(ImageType.GUI_BACKGROUND);

    @Override
    public void update() {
        ShopItem displayItem = game.shop.helpDisplayItem;
        Strings.generateNewShieldDescription();
        background.draw();
        displayItem.icon.setPosition(5, 10);
        displayItem.icon.draw();
        if (displayItem.id == ShopItem.ID.SHIELD) {
            displayItem.description = Strings.generateNewShieldDescription().toString();
        }
        Printer.print("<[" + displayItem.name + "]>", Color.YELLOW, 25, 14);
        Printer.print(displayItem.description, 4, 30);
        closeButton.draw();
        super.update();
    }
}
