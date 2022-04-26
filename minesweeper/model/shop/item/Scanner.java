package com.javarush.games.minesweeper.model.shop.item;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.shop.Shop;

public class Scanner extends ShopItem {
    private final AimFrame frame;

    public Scanner(MinesweeperGame game) {
        super(game);
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_SCANNER);
        name = "Сканер";
        description = getScannerDescription();
        cost = 8 + Options.difficulty / 5;
        inStock = 1;
        frame = new AimFrame(game.getCell(4, 4), -9, ImageType.BOARD_SCANNER_FRAME);
    }

    public boolean tryToUseOrMoveFrame(Cell cell) {
        if (!isActivated) {
            return false;
        }

        if (frame.isNotFocusedOnCell(cell)) {
            frame.focusOnCell(cell);
            return true;
        }

        use(cell);
        return true;
    }

    private void use(Cell cell) {
        deactivate();
        game.scanNeighbors(cell.x, cell.y);

        final Shop shop = game.getShop();
        shop.getBomb().restock();
        restock();
    }

    @Override
    public void activate() {
        if (unableToActivate()) return;
        isActivated = true;
        game.getShop().getBomb().empty();
    }

    private boolean unableToActivate() {
        if (game.isStopped() || isActivated) return true;
        return game.getShop().getBomb().isActivated;
    }

    public void drawFrame() {
        if (!isActivated) return;
        game.setDisplayInterlace(false);
        frame.draw();
    }

    private String getScannerDescription() {
        return "Откроет безопасную\n" +
                "клетку в указанном\n" +
                "поле размером 3*3.\n" +
                "Клик по клетке:\n" +
                "   передвинуть поле\n" +
                "Клик в центр поля:\n" +
                "   сканировать поле";
    }
}
