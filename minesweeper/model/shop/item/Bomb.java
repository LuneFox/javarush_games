package com.javarush.games.minesweeper.model.shop.item;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.shop.Shop;

public class Bomb extends ShopItem {
    private final AimFrame frame;

    public Bomb(MinesweeperGame game) {
        super(game);
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_BOMB);
        name = "Мини-бомба";
        description = getBombDescription();
        cost = 6 + Options.difficulty / 10;
        inStock = 1;
        int framePadding = -3;
        frame = new AimFrame(game.getCell(4, 4), framePadding, ImageType.BOARD_BOMB_FRAME);
    }

    public void aimOrUse(Cell cell) {
        if (!isActivated) return;

        if (frame.isNotFocusedOnCell(cell)) {
            frame.focusOnCell(cell);
            return;
        }
        use(cell);
    }

    private void use(Cell cell) {
        this.deactivate();
        game.useMiniBomb(cell);

        final Shop shop = game.getShop();
        shop.getScanner().restock();
        shop.getDice().hide();
        restock();
    }

    @Override
    public void activate() {
        if (unableToActivate()) return;
        isActivated = true;
        game.getShop().getScanner().empty();
    }

    private boolean unableToActivate() {
        if (game.isStopped() || isActivated) return true;
        return game.getShop().getBomb().isActivated();
    }

    public void drawFrame() {
        if (!isActivated) return;
        game.setInterlacedEffect(false);
        frame.draw();
    }

    private String getBombDescription() {
        return "Бросив бомбочку, вы\n" +
                "уничтожите закрытую\n" +
                "клетку на поле.\n" +
                "Если взорвёте мину,\n" +
                "соседние мины тоже\n" +
                "взорвутся по цепи.\n" +
                "Очков не даёт.";
    }
}
