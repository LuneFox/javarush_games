package com.javarush.games.minesweeper.model.shop.item;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.Cell;

public class Bomb extends ShopItem {
    private final Image frame;

    public Bomb() {
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_BOMB);
        name = "Мини-бомба";
        description = "Бросив бомбочку, вы\n" +
                "уничтожите закрытую\n" +
                "клетку на поле.\n" +
                "Если взорвёте мину,\n" +
                "соседние мины тоже\n" +
                "взорвутся по цепи.\n" +
                "Очков не даёт.";
        cost = 6 + Options.difficulty / 10;
        inStock = 1;
        frame = Image.cache.get(ImageType.GUI_SURROUND_FRAME);
    }

    public boolean use(Cell cell) {
        if (!isActivated) return false;

        this.deactivate();
        game.shop.dice.hide();
        game.shop.restock(game.shop.bomb, 1);
        game.shop.restock(game.shop.scanner, 1);
        game.boardManager.destroyCell(cell.x, cell.y);
        return true;
    }

    @Override
    public void activate() {
        if (game.isStopped || isActivated) return;
        if (game.shop.bomb.isActivated()) return;
        isActivated = true;
        frame.replaceColor(Color.RED, 3);
        game.shop.scanner.inStock = 0;
    }

    public void drawFrame() {
        if (!isActivated) return;
        frame.draw();
    }
}
