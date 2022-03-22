package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.player.Inventory;
import com.javarush.games.minesweeper.model.shop.ShopItem;

public class FlagManager {
    private final MinesweeperGame game;
    private Field field;

    public FlagManager(MinesweeperGame game) {
        this.game = game;
    }

    // Using this on flagged cell with "flagIsRemovable = true" will return the flag to inventory
    void setFlag(int x, int y, boolean flagIsRemovable) {
        if (game.isStopped) return;
        Cell cell = field.get()[y][x];
        if (cell.isOpen) return;
        if (cell.isFlagged && flagIsRemovable) {
            returnFlagToInventory(cell);
        } else {
            placeFlagFromInventory(cell);
        }
    }

    void returnFlagToInventory(Cell cell) {
        game.player.inventory.add(ShopItem.ID.FLAG);
        cell.isFlagged = false;
        if (cell.isMined) {
            cell.setSprite(ImageType.BOARD_MINE);
        } else if (cell.isNumerable()) {
            cell.setSprite(cell.countMinedNeighbors);
        } else {
            cell.setSprite(ImageType.NONE);
        }
    }

    void placeFlagFromInventory(Cell cell) {
        Inventory inventory = game.player.inventory;
        if (inventory.hasNoFlags()) game.shop.offerFlag();
        if (inventory.hasNoFlags()) return;
        if (cell.isFlagged) return;
        inventory.remove(ShopItem.ID.FLAG);
        cell.isFlagged = true;
        cell.setSprite(ImageType.BOARD_FLAG);
    }

    void retrieveFlag(Cell cell) {
        if (cell.isFlagged) {
            game.shop.restock(game.shop.flag, 1);
            cell.isFlagged = false;
        }
    }

    public void setField(Field field) {
        this.field = field;
    }
}
