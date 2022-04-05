package com.javarush.games.minesweeper.model.shop.item;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.shop.Shop;

public class Bomb extends ShopItem {
    private final Image frame;

    public Bomb(MinesweeperGame game) {
        super(game);
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_BOMB);
        name = "Мини-бомба";
        description = "Бросив бомбочку, вы\n" + "уничтожите закрытую\n" + "клетку на поле.\n" + "Если взорвёте мину,\n" + "соседние мины тоже\n" + "взорвутся по цепи.\n" + "Очков не даёт.";
        cost = 6 + Options.difficulty / 10;
        inStock = 1;
        frame = Image.cache.get(ImageType.GUI_SURROUND_FRAME);
    }

    public boolean use(Cell cell) {
        if (!isActivated) return false;

        this.deactivate();
        game.destroyCell(cell.x, cell.y);

        final Shop shop = game.getShop();
        shop.getDice().hide();
        shop.restock(shop.getScanner(), 1);
        shop.restock(shop.getBomb(), 1);

        return true;
    }

    @Override
    public void activate() {
        if (game.isStopped() || isActivated) return;
        if (game.getShop().getBomb().isActivated()) return;
        isActivated = true;
        frame.replaceColor(Color.RED, 3);
        game.getShop().getScanner().setInStock(0);
    }

    public void drawFrame() {
        if (!isActivated) return;
        frame.draw();
    }
}
