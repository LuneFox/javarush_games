package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.player.Inventory;

public class FlagManager {
    private final MinesweeperGame game;
    private final Field field;

    FlagManager(MinesweeperGame game, Field field) {
        this.game = game;
        this.field = field;
    }

    void swapFlag(int x, int y) {
        if (game.isStopped()) return;
        Cell cell = field.get()[y][x];
        if (cell.isOpen()) return;
        if (cell.isFlagged()) {
            returnFlagToInventory(cell);
        } else {
            placeFlagFromInventory(cell);
        }
    }

    void setFlag(int x, int y) {
        if (game.isStopped()) return;
        Cell cell = field.get()[y][x];
        if (cell.isOpen()) return;
        placeFlagFromInventory(cell);
    }

    void returnFlagToShop(Cell cell) {
        if (!cell.isFlagged()) return;
        game.getShop().restock(game.getShop().getFlag(), 1);
        cell.setFlagged(false);
    }

    private void returnFlagToInventory(Cell cell) {
        game.getPlayer().getInventory().add(game.getShop().getFlag());
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
        Inventory inventory = game.getPlayer().getInventory();
        if (inventory.hasNoFlags()) game.getShop().offerFlag();
        if (inventory.hasNoFlags()) return;
        if (cell.isFlagged()) return;
        inventory.remove(game.getShop().getFlag());
        cell.setSprite(ImageType.BOARD_FLAG);
        cell.setFlagged(true);
    }
}
