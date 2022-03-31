package com.javarush.games.minesweeper.model.shop.item;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.ShopItemStatusBar;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;

public class Shovel extends ShopItem {
    protected ShopItemStatusBar statusBar;
    public Shovel() {
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_SHOVEL);
        name = "Золотая лопата";
        description = "Следующие 5 шагов\n" +
                "вы будете получать\n" +
                "в два раза больше\n" +
                "золотых монет.\n" +
                "Золото добывается\n" +
                "при открытии клеток\n" +
                "с цифрами.";
        cost = 9;
        inStock = 1;
        effectDuration = 5;
        statusBar = new ShopItemStatusBar(99, Color.DARKORANGE, this);
    }

    @Override
    public void activate() {
        if (game.isStopped || isActivated) return;
        isActivated = true;
        setExpireMove(game.player.getMoves() + effectDuration);
    }

    public void draw(){
        statusBar.draw();
    }
}
