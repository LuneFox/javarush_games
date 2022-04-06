package com.javarush.games.minesweeper.model.shop.item;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.shop.Shop;

public class Bomb extends ShopItem {
    private final Image frame;
    private final int frameMoveSpeed;
    private int frameX;
    private int frameY;
    private Cell focusCell;

    public Bomb(MinesweeperGame game) {
        super(game);
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_BOMB);
        name = "Мини-бомба";
        description = "Бросив бомбочку, вы\n" + "уничтожите закрытую\n" + "клетку на поле.\n" + "Если взорвёте мину,\n" + "соседние мины тоже\n" + "взорвутся по цепи.\n" + "Очков не даёт.";
        cost = 6 + Options.difficulty / 10;
        inStock = 1;
        frame = Image.cache.get(ImageType.BOARD_BOMB_FRAME);
        focusCell = game.getCell(4, 4);
        frameX = getFrameDestX();
        frameY = getFrameDestY();
        frameMoveSpeed = 5;
    }

    public boolean use(Cell cell) {
        if (!isActivated) return false;

        if (focusCell != cell) {
            focusCell = cell;
            return true;
        }

        this.deactivate();
        game.destroyCell(cell.x, cell.y);

        final Shop shop = game.getShop();
        shop.getDice().hide();
        restock();
        shop.getScanner().restock();

        return true;
    }

    @Override
    public void activate() {
        if (game.isStopped() || isActivated) return;
        if (game.getShop().getBomb().isActivated()) return;
        isActivated = true;
        game.getShop().getScanner().empty();
    }

    public void drawFrame() {
        if (!isActivated) return;
        game.setDisplayInterlace(false);
        if (frameX < getFrameDestX()) frameX += frameMoveSpeed;
        if (frameX > getFrameDestX()) frameX -= frameMoveSpeed;
        if (frameY < getFrameDestY()) frameY += frameMoveSpeed;
        if (frameY > getFrameDestY()) frameY -= frameMoveSpeed;
        frame.draw(frameX, frameY);
    }

    private int getFrameDestX() {
            return focusCell.x * 10 - 2;
    }

    private int getFrameDestY() {
            return focusCell.y * 10 - 2;
    }
}
