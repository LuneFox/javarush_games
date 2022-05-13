package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.player.Player;
import com.javarush.games.minesweeper.model.shop.Shop;

public class FlagManager {
    private final MinesweeperGame game;

    FlagManager(MinesweeperGame game) {
        this.game = game;
    }

    void swapFlag(Cell cell) {
        if (cell.isFlagged()) {
            returnFlagToPlayerInventory(cell);
        } else {
            placeFlagFromPlayerInventory(cell);
        }
    }

    void returnFlagToShop(Cell cell) {
        if (!cell.isFlagged()) return;
        game.getShop().getFlag().putToStock();
        cell.setFlagged(false);
    }

    void returnFlagToPlayerInventory(Cell cell) {
        if (game.isStopped()) return;
        if (cell.isOpen()) return;
        final Shop shop = game.getShop();
        final Player player = game.getPlayer();
        player.gainItem(shop.getFlag());
        cell.setFlagged(false);
    }

    void placeFlagFromPlayerInventory(Cell cell) {
        if (game.isStopped()) return;
        if (cell.isOpen()) return;

        final Shop shop = game.getShop();
        final Player player = game.getPlayer();

        if (player.countFlags() == 0) shop.offerFlag();

        if (player.countFlags() == 0) return;
        if (cell.isFlagged()) return;

        player.loseItem(shop.getFlag());
        cell.setFlagged(true);
    }
}
