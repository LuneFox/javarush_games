package com.javarush.games.minesweeper.model.shop.item;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.Cell;

public class Shield extends ShopItem {
    private final int difficultyModifier = Options.difficulty / 5;

    public Shield(MinesweeperGame game) {
        super(game);
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_SHIELD);
        name = "Сапёрский щит";

        description = "Спасёт от взрыва\n" +
                "при открытии мины\n" +
                "один раз. Однако вы\n" +
                "потеряете " + 150 * difficultyModifier + " очков.";
        cost = 13 + difficultyModifier;
        inStock = 1;
    }

    public boolean use(Cell cell) {
        if (!isActivated) return false;
        deactivate();

        cell.setSprite(ImageType.BOARD_MINE);
        cell.setShielded(true);
        cell.setBackgroundColor(Color.YELLOW);

        game.getShop().restock(game.getShop().getShield(), 1);
        game.getPlayer().getScore().setLostScore(game.getPlayer().getScore().getLostScore() - 150 * difficultyModifier);
        game.getPlayer().incBrokenShields();

        PopUpMessage.show("Щит разрушен!");
        return true;
    }

    @Override
    public void activate() {
        if (game.isStopped() || isActivated) return;
        isActivated = true;
    }
}
