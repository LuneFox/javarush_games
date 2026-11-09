package com.javarush.games.minesweeper.model.shop.items;

import com.javarush.games.minesweeper.Text;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.board.field.Cell;

/** Флажок для отметки мин. */
public class Flag extends ShopItem {
    /** Создаёт экземпляр Flag. */
    public Flag(MinesweeperGame game) {
        super(game);
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_FLAG);
        name = Text.ITEM_FLAG_NAME.getText();
        description = Text.ITEM_FLAG_DESCRIPTION.getText();
        cost = 1;
        inStock = game.countCells(Cell::isMined);
    }

    /** Активирует предмет. */
    @Override
    public void activate() {
        // Не активируется
    }

}
