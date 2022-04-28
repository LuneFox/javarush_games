package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.player.Inventory;
import com.javarush.games.minesweeper.model.shop.Shop;

public class FlagManager {
    private final MinesweeperGame game;
    private final Field field;

    FlagManager(MinesweeperGame game, Field field) {
        this.game = game;
        this.field = field;
    }

    void swapFlag(int x, int y) {
        if (game.isStopped()) return;
        Cell cell = field.getField()[y][x];
        if (cell.isOpen()) return;
        if (cell.isFlagged()) {
            returnFlagToInventory(cell);
        } else {
            placeFlagFromInventory(cell);
        }
    }

    void setFlag(int x, int y) {
        if (game.isStopped()) return;
        Cell cell = field.getField()[y][x];
        if (cell.isOpen()) return;
        placeFlagFromInventory(cell);
    }

    void returnFlagToShop(Cell cell) {
        if (!cell.isFlagged()) return;
        game.getShop().getFlag().restock();
        cell.setFlagged(false);
    }

    private void returnFlagToInventory(Cell cell) {
        final Shop shop = game.getShop();
        final Inventory inventory = game.getPlayer().getInventory();
        inventory.put(shop.getFlag());
        cell.setFlagged(false);
    }

    private void placeFlagFromInventory(Cell cell) {
        final Inventory inventory = game.getPlayer().getInventory();
        final Shop shop = game.getShop();

        if (inventory.countFlags() == 0) shop.offerFlag();
        if (inventory.countFlags() == 0) return;
        if (cell.isFlagged()) return;
        inventory.remove(game.getShop().getFlag());
        // cell.setSprite(ImageType.BOARD_FLAG);
        cell.setFlagged(true);
    }
}
