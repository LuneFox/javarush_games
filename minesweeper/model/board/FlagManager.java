package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.player.Inventory;
import com.javarush.games.minesweeper.model.shop.ShopItem;

public class FlagManager {
    private final MinesweeperGame game;
    private final Field field;

    FlagManager(MinesweeperGame game, Field field) {
        this.game = game;
        this.field = field;
    }

    void swapFlag(int x, int y) {
        if (game.isStopped) return;
        Cell cell = field.get()[y][x];
        if (cell.isOpen()) return;
        if (cell.isFlagged()) {
            returnFlagToInventory(cell);
        } else {
            placeFlagFromInventory(cell);
        }
    }

    void setFlag(int x, int y) {
        if (game.isStopped) return;
        Cell cell = field.get()[y][x];
        if (cell.isOpen()) return;
        placeFlagFromInventory(cell);
    }

    void returnFlagToShop(Cell cell) {
        if (!cell.isFlagged()) return;
        game.shop.restock(game.shop.flag, 1);
        cell.setFlagged(false);
    }

    private void returnFlagToInventory(Cell cell) {
        game.player.inventory.add(ShopItem.ID.FLAG);
        if (cell.isMined()) {
            cell.setSprite(ImageType.BOARD_MINE);
        } else if (cell.isNumerable()) {
            cell.setSprite(cell.getCountMinedNeighbors());
        } else {
            cell.setSprite(ImageType.NONE);
        }
        cell.setFlagged(false);
    }

    private void placeFlagFromInventory(Cell cell) {
        Inventory inventory = game.player.inventory;
        if (inventory.hasNoFlags()) game.shop.offerFlag();
        if (inventory.hasNoFlags()) return;
        if (cell.isFlagged()) return;
        inventory.remove(ShopItem.ID.FLAG);
        cell.setSprite(ImageType.BOARD_FLAG);
        cell.setFlagged(true);
    }
}