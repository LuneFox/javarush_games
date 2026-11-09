package com.javarush.games.minesweeper.model.shop.items;

import com.javarush.games.minesweeper.Text;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.shop.Shop;

/** Мини-бомба, уничтожающая клетку. */
public class Bomb extends ShopItem {
    /** Рамка прицела или ячейки. */
    private final AimFrame frame;

    /** Создаёт экземпляр Bomb. */
    public Bomb(MinesweeperGame game) {
        super(game);
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_BOMB);
        name = Text.ITEM_BOMB_NAME.getText();
        description = Text.ITEM_BOMB_DESCRIPTION.getText();
        cost = 6 + Options.getDifficulty() / 10;
        inStock = 1;
        int framePadding = -3;
        frame = new AimFrame(game.getCell(4, 4), framePadding, ImageType.BOARD_BOMB_FRAME);
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
        if (!game.destroyCell(cell)) return;

        this.deactivate();

        final Shop shop = game.getShop();
        shop.getScanner().putToStock();
        shop.getDice().hide();
        putToStock();
    }

    /** Активирует предмет. */
    @Override
    public void activate() {
        if (unableToActivate()) return;
        isActivated = true;
        game.getShop().getScanner().emptyStock();
    }

    /** Сообщает, нельзя ли активировать предмет. */
    private boolean unableToActivate() {
        if (game.isStopped() || isActivated) return true;
        return game.getShop().getScanner().isActivated();
    }

    /** Рисует рамку прицела. */
    public void drawFrame() {
        if (!isActivated) return;
        game.setInterlacedEffect(false);
        frame.draw();
    }

}
