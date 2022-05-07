package com.javarush.games.minesweeper.model.shop.item;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.board.field.CellFilter;

public class Flag extends ShopItem {
    public Flag(MinesweeperGame game) {
        super(game);
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_FLAG);
        name = "Флажок";
        description = getFlagDescription();
        cost = 1;
        inStock = game.countCells(CellFilter.MINED);
    }

    @Override
    public void activate() {
        // Cannot be activated
    }

    private String getFlagDescription() {
        return "Обычный флажок\n" +
                "для установки на\n" +
                "поле. Вы можете\n" +
                "сэкономить золото,\n" +
                "не покупая флажки,\n" +
                "когда позиция мины\n" +
                "очевидна.";
    }
}
