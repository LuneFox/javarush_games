package com.javarush.games.minesweeper.model.board;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.player.Player;
import com.javarush.games.minesweeper.model.shop.Shop;

/** Ставит и возвращает флажки. */
public class FlagManager {
    /** Ссылка на игру. */
    private final MinesweeperGame game;

    /** Создаёт экземпляр FlagManager. */
    FlagManager(MinesweeperGame game) {
        this.game = game;
    }

    /** Переключает флажок на клетке. */
    void toggleFlag(Cell cell) {
        if (cell.isFlagged()) {
            returnFlagToPlayer(cell);
        } else {
            placeFlag(cell);
        }
    }

    /** Возвращает флажок на склад. */
    void returnFlagToShop(Cell cell) {
        if (!cell.isFlagged()) return;

        game.getShop().getFlag().putToStock();
        cell.setFlagged(false);
    }

    /** Возвращает флажок игроку. */
    void returnFlagToPlayer(Cell cell) {
        if (game.isStopped() || cell.isOpen() || !cell.isFlagged()) return;

        final Shop shop = game.getShop();
        final Player player = game.getPlayer();

        player.gainItem(shop.getFlag());
        cell.setFlagged(false);
    }

    /** Ставит флажок из инвентаря. */
    void placeFlag(Cell cell) {
        if (game.isStopped() || cell.isOpen() || cell.isFlagged()) return;

        final Shop shop = game.getShop();
        final Player player = game.getPlayer();

        if (player.countFlags() == 0) shop.offerFlag();
        if (player.countFlags() == 0) return;

        player.loseItem(shop.getFlag());
        cell.setFlagged(true);
    }
}
