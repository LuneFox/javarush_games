package com.javarush.games.minesweeper.view.impl;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.model.Phase;
import com.javarush.games.minesweeper.model.shop.item.ShopItem;
import com.javarush.games.minesweeper.view.View;

public class ViewItemHelp extends View {

    private final Button closeButton = new Button(88, 2, "x", this) {
        @Override
        public void onLeftClick() {
            super.onLeftClick();
            Phase.setActive(Phase.SHOP);
        }
    };
    final Image background = Image.cache.get(ImageType.GUI_BACKGROUND);

    public ViewItemHelp(MinesweeperGame game) {
        super(game);
    }

    @Override
    public void update() {
        ShopItem displayItem = game.getShop().getHelpDisplayItem();
        background.draw();
        displayItem.drawIcon(5, 10);
        Printer.print("<[" + displayItem.getName() + "]>", Color.YELLOW, 25, 14);
        Printer.print(displayItem.getDescription(), 4, 30);
        closeButton.draw();
        super.update();
    }
}
