package com.javarush.games.minesweeper.model.shop.items;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.shop.Shop;

public class Scanner extends ShopItem {
    private final AimFrame frame;

    public Scanner(MinesweeperGame game) {
        super(game);
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_SCANNER);
        name = "Сканер";
        description = getScannerDescription();
        cost = 8 + Options.getDifficulty() / 5;
        inStock = 1;
        int framePadding = -9;
        frame = new AimFrame(game.getCell(4, 4), framePadding, ImageType.BOARD_SCANNER_FRAME);
    }

    public void aim(Cell cell) {
        if (!isActivated) return;

        if (frame.isNotFocusedOnCell(cell)) {
            frame.focusOnCell(cell);
            return;
        }

        use(cell);
    }

    private void use(Cell cell) {
        deactivate();
        game.scanNeighbors(cell);

        final Shop shop = game.getShop();
        shop.getBomb().putToStock();
        putToStock();
    }

    @Override
    public void activate() {
        if (unableToActivate()) return;
        isActivated = true;
        game.getShop().getBomb().emptyStock();
    }

    private boolean unableToActivate() {
        if (game.isStopped() || isActivated) return true;
        return game.getShop().getBomb().isActivated;
    }

    public void drawFrame() {
        if (!isActivated) return;
        game.setInterlacedEffect(false);
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
