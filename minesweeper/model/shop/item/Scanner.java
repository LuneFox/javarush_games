package com.javarush.games.minesweeper.model.shop.item;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.Cell;
import com.javarush.games.minesweeper.model.shop.Shop;

public class Scanner extends ShopItem {
    private final Image frame;
    private final int frameMoveSpeed;
    private int frameX;
    private int frameY;
    private Cell focusCell;

    public Scanner(MinesweeperGame game) {
        super(game);
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_SCANNER);
        name = "Сканер";
        description = "Откроет безопасную\n" +
                "клетку в указанном\n" +
                "поле размером 3*3.\n" +
                "Клик по клетке:\n" +
                "   передвинуть поле\n" +
                "Клик в центр поля:\n" +
                "   сканировать поле";
        cost = 8 + Options.difficulty / 5;
        inStock = 1;
        frame = Image.cache.get(ImageType.BOARD_SCANNER_FRAME);
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

        deactivate();
        game.scanNeighbors(cell.x, cell.y);

        final Shop shop = game.getShop();
        restock();
        shop.getBomb().restock();

        return true;
    }

    @Override
    public void activate() {
        if (game.isStopped() || isActivated) return;
        if (game.getShop().getBomb().isActivated) return;
        isActivated = true;
        game.getShop().getBomb().empty();
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
        return focusCell.x * 10 - 9;
    }

    private int getFrameDestY() {
        return focusCell.y * 10 - 9;
    }
}
