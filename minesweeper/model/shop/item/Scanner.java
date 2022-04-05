package com.javarush.games.minesweeper.model.shop.item;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.Cell;

public class Scanner extends ShopItem {
    private final Image frame;

    public Scanner(MinesweeperGame game) {
        super(game);
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_SCANNER);
        name = "Сканер";
        description = "Откроет безопасную\n" +
                "клетку в поле 3*3\n" +
                "вокруг курсора.\n" +
                "Если таких нет,\n" +
                "расставит флажки\n" +
                "над минами, подарив\n" +
                "нехватающие.";
        cost = 8 + Options.difficulty / 5;
        inStock = 1;
        frame = Image.cache.get(ImageType.GUI_SURROUND_FRAME);
    }

    public boolean use(Cell cell) {
        if (!isActivated) return false;
        deactivate();
        game.scanNeighbors(cell.x, cell.y);
        game.restockScannerAndBomb();
        return true;
    }

    @Override
    public void activate() {
        if (game.isStopped() || isActivated) return;
        if (game.getShop().getBomb().isActivated) return;
        isActivated = true;
        frame.replaceColor(Color.BLUE, 3);
        game.getShop().getBomb().setInStock(0);
    }

    public void drawFrame() {
        if (!isActivated) return;
        frame.draw();
    }
}
