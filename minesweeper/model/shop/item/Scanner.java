package com.javarush.games.minesweeper.model.shop.item;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.Cell;

public class Scanner extends ShopItem {
    private final Image frame;

    public Scanner() {
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
        game.boardManager.scanNeighbors(cell.x, cell.y);
        game.shop.restock(game.shop.scanner, 1);
        game.shop.restock(game.shop.bomb, 1);

        return true;
    }

    @Override
    public void activate() {
        if (game.isStopped || isActivated) return;
        if (game.shop.bomb.isActivated()) return;
        isActivated = true;
        frame.replaceColor(Color.BLUE, 3);
        game.shop.bomb.inStock = 0;
    }

    public void drawFrame() {
        if (!isActivated) return;
        frame.draw();
    }
}
