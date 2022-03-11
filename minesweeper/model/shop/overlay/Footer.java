package com.javarush.games.minesweeper.model.shop.overlay;

import com.javarush.games.minesweeper.gui.Printer;
import com.javarush.games.minesweeper.gui.Theme;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.model.DrawableObject;

public class Footer extends DrawableObject {
    private Image panel;

    public Footer() {
        panel = Image.cache.get(ImageType.WIN_SHOP_HEADER_FOOTER);
        this.x = 10;
        this.y = 78;
        this.height = panel.height;
        this.width = panel.width;
    }

    @Override
    public void draw() {
        panel = Image.cache.get(ImageType.WIN_SHOP_HEADER_FOOTER);
        panel.draw(x, y);
        Printer.print("Очки:" + game.player.score.getCurrentScore(), Theme.SHOP_SCORE.getColor(), x + 3, y + 2);
        Printer.print("Шаги:" + game.player.getMoves(), Theme.SHOP_MOVES.getColor(), x + 73, y + 2, true);
    }
}
