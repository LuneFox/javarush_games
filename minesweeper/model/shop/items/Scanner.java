package com.javarush.games.minesweeper.model.shop.items;

import com.javarush.games.minesweeper.Text;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.shop.Shop;

/** Сканер, открывающий безопасную клетку. */
public class Scanner extends ShopItem {
    /** Рамка прицела или ячейки. */
    private final AimFrame frame;

    /** Создаёт экземпляр Scanner. */
    public Scanner(MinesweeperGame game) {
        super(game);
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_SCANNER);
        name = Text.ITEM_SCANNER_NAME.getText();
        description = Text.ITEM_SCANNER_DESCRIPTION.getText();
        cost = 8 + Options.getDifficulty() / 5;
        inStock = 1;
        int framePadding = -9;
        frame = new AimFrame(game.getCell(4, 4), framePadding, ImageType.BOARD_SCANNER_FRAME);
    }

    /** Наводит предмет на клетку. */
    public void aim(Cell cell) {
        if (!isActivated) return;

        if (frame.isNotFocusedOnCell(cell)) {
            frame.focusOnCell(cell);
            return;
        }

        use(cell);
    }

    /** Использует предмет. */
    private void use(Cell cell) {
        deactivate();
        game.scanNeighbors(cell);

        final Shop shop = game.getShop();
        shop.getBomb().putToStock();
        putToStock();
    }

    /** Активирует предмет. */
    @Override
    public void activate() {
        if (unableToActivate()) return;
        isActivated = true;
        game.getShop().getBomb().emptyStock();
    }

    /** Сообщает, нельзя ли активировать предмет. */
    private boolean unableToActivate() {
        if (game.isStopped() || isActivated) return true;
        return game.getShop().getBomb().isActivated;
    }

    /** Рисует рамку прицела. */
    public void drawFrame() {
        if (!isActivated) return;
        game.setInterlacedEffect(false);
        frame.draw();
    }

}
