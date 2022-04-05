package com.javarush.games.minesweeper.model.shop.item;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.board.Cell;

public class Flag extends ShopItem {
    public Flag(MinesweeperGame game) {
        super(game);
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_FLAG);
        name = "Флажок";
        description = "Обычный флажок\n" +
                "для установки на\n" +
                "поле. Вы можете\n" +
                "сэкономить золото,\n" +
                "не покупая флажки,\n" +
                "когда позиция мины\n" +
                "очевидна.";
        cost = 1;
        inStock = game.countAllCells(Cell.Filter.MINED);
    }

    @Override
    public void activate() {
        // Cannot be activated
    }
}
