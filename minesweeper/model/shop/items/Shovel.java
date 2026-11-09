package com.javarush.games.minesweeper.model.shop.items;

import com.javarush.games.minesweeper.Text;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.ShopItemStatusBar;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;

/** Золотая лопата, удваивающая золото. */
public class Shovel extends ShopItem {
    /** Полоса состояния предмета. */
    protected final ShopItemStatusBar statusBar;

    /** Создаёт экземпляр Shovel. */
    public Shovel(MinesweeperGame game) {
        super(game);
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_SHOVEL);
        name = Text.ITEM_SHOVEL_NAME.getText();
        description = Text.ITEM_SHOVEL_DESCRIPTION.getText();
        cost = 9;
        inStock = 1;
        effectDuration = 5;
        statusBar = new ShopItemStatusBar(99, Color.DARKORANGE, this);
    }

    /** Активирует предмет. */
    @Override
    public void activate() {
        lease();
    }

    /** Рисует полосу состояния. */
    public void drawStatusBar() {
        statusBar.draw();
    }

}
