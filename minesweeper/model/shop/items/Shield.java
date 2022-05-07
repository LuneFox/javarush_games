package com.javarush.games.minesweeper.model.shop.items;

import com.javarush.games.minesweeper.MinesweeperGame;
import com.javarush.games.minesweeper.gui.PopUpMessage;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Options;
import com.javarush.games.minesweeper.model.board.field.Cell;
import com.javarush.games.minesweeper.model.player.Score;

public class Shield extends ShopItem {
    private final int difficultyModifier = Options.difficulty / 5;

    public Shield(MinesweeperGame game) {
        super(game);
        icon = Image.cache.get(ImageType.SHOP_SHOWCASE_SHIELD);
        name = "Сапёрский щит";
        description = getShieldDescription();
        cost = 13 + difficultyModifier;
        inStock = 1;
    }

    public void use(Cell cell) {
        deactivate();
        cell.setShielded(true);
        affectScore();
        putToStock();
        PopUpMessage.show("Щит разрушен!");
    }

    private void affectScore() {
        final Score score = game.getPlayer().getScore();
        score.setLostScore(score.getLostScore() - 150 * difficultyModifier);
        game.getPlayer().increaseBrokenShieldsCounter();
    }

    @Override
    public void activate() {
        if (game.isStopped() || isActivated) return;
        isActivated = true;
    }

    private String getShieldDescription() {
        return "Спасёт от взрыва\n" +
                "при открытии мины\n" +
                "один раз. Однако вы\n" +
                "потеряете " + 150 * difficultyModifier + " очков.";
    }
}
