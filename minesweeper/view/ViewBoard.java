package com.javarush.games.minesweeper.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageType;
import com.javarush.games.minesweeper.model.Phase;

public class ViewBoard extends View {
    Image frame = Image.cache.get(ImageType.GUI_SURROUND_FRAME);

    public ViewBoard(Phase phase) {
        super(phase);
    }

    @Override
    public void update() {
        if (!(Phase.isActive(Phase.SHOP))) game.display.setInterlaceEnabled(true);
        game.timerTick();
        game.field.draw();

        if (game.shop.allItems.get(1).isActivated()) {
            frame.replaceColor(Color.BLUE, 3);
            frame.draw();
        } else if (game.shop.allItems.get(5).isActivated()) {
            frame.replaceColor(Color.RED, 3);
            frame.draw();
        }

        game.timer.draw();
        game.shop.goldenShovel.statusBar.draw();
        game.shop.luckyDice.statusBar.draw();
        super.update();
    }
}
