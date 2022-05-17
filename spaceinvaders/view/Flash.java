package com.javarush.games.spaceinvaders.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;

public class Flash {
    private final SpaceInvadersGame game;
    public boolean colorDecider;
    public boolean show;

    public Flash(SpaceInvadersGame game) {
        this.game = game;
    }

    public void draw() {
        if (show) {
            for (int y = 0; y < SpaceInvadersGame.HEIGHT; y++) {
                for (int x = 0; x < SpaceInvadersGame.WIDTH; x++) {
                    if (y % 2 == 0 && x % 2 == 0) {
                        colorDecider = !colorDecider;
                        game.display.drawPixel(x, y, colorDecider ? Color.WHITE : Color.YELLOW);
                    }
                }
            }
            show = false;
        }
    }

    public void show() {
        show = true;
    }
}
