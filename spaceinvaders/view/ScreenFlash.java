package com.javarush.games.spaceinvaders.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.Drawable;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;
import com.javarush.games.spaceinvaders.model.GameField;

/** Одноразовая бело-жёлтая вспышка на всё поле. */
public class ScreenFlash implements Drawable {
    private final SpaceInvadersGame game;

    private boolean requested;
    private boolean white;

    /** Создаёт вспышку для игры. */
    public ScreenFlash(SpaceInvadersGame game) {
        this.game = game;
    }

    /** Заказывает вспышку. */
    public void request() {
        requested = true;
    }

    /** Рисует вспышку, если она заказана. */
    @Override
    public void draw() {
        if (!requested) return;

        for (int y = 0; y < GameField.HEIGHT; y++) {
            for (int x = 0; x < GameField.WIDTH; x++) {
                if (y % 2 != 0 || x % 2 != 0) continue;

                white = !white;
                game.getScreen().setPixel(x, y, white ? Color.WHITE : Color.YELLOW);
            }
        }

        requested = false;
    }
}
