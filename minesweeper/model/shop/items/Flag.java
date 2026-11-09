package com.javarush.games.minesweeper.model.shop.items;

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
        name = "Флажок";
        description = getFlagDescription();
        cost = 1;
        inStock = game.countCells(Cell::isMined);
    }

    /** Активирует предмет. */
    @Override
    public void activate() {
        // Не активируется
    }

    /** Возвращает описание флажка. */
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
