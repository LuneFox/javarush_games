package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.player.Inventory;
import com.javarush.games.minesweeper.model.player.Player;
import com.javarush.games.minesweeper.model.shop.Shop;

public class FlagManager {
    private final MinesweeperGame game;

    FlagManager(MinesweeperGame game) {
        this.game = game;
    }

    void swapFlag(Cell cell) {
        if (cell.isFlagged()) {
            returnFlagToInventory(cell);
        } else {
            placeFlagFromInventory(cell);
        }
    }

    void returnFlagToShop(Cell cell) {
        if (!cell.isFlagged()) return;
        game.getShop().getFlag().restock();
        cell.setFlagged(false);
    }

    void returnFlagToInventory(Cell cell) {
        if (game.isStopped()) return;
        if (cell.isOpen()) return;
        final Shop shop = game.getShop();
        final Player player = game.getPlayer();
        final Inventory inventory = player.getInventory();

        inventory.put(shop.getFlag());
        cell.setFlagged(false);
    }

    void placeFlagFromInventory(Cell cell) {
        if (game.isStopped()) return;
        if (cell.isOpen()) return;
        final Shop shop = game.getShop();
        final Player player = game.getPlayer();
        final Inventory inventory = player.getInventory();

        if (inventory.countFlags() == 0) shop.offerFlag();
        if (inventory.countFlags() == 0) return;
        if (cell.isFlagged()) return;
        inventory.remove(game.getShop().getFlag());
        cell.setFlagged(true);
    }
}
