package com.javarush.games.snake;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.enums.Element;
import com.javarush.games.snake.enums.Graphics;

import java.util.ArrayList;

public class Menu {
    private SnakeGame game;

    public Menu(SnakeGame game) {
        this.game = game;
    }

    void displayMain() {
        Screen.set(Screen.Type.MAIN_MENU);
        game.stopTurnTimer();
        game.createOrbsForMenu();
        game.drawScene();

        new DockMessage("ALCHEMY SNAKE VER " + SnakeGame.VERSION, Color.LIGHTGREEN).draw(game);

        String selector1 = (Signs.currentSetting == Graphics.KANJI ? "■" : "□");
        String selector2 = (Signs.currentSetting == Graphics.EMOJI ? "■" : "□");
        new DockMessage("SELECT ICONS: " + selector1 + " KANJI", Color.SKYBLUE).draw(game, 3);
        new DockMessage(" (UP, DOWN)   " + selector2 + " EMOJI", Color.SKYBLUE).draw(game, 5);

        new DockMessage("COLLECT THESE TO WIN:", Color.YELLOW).draw(game, 7);
        new DockMessage("WATER ORB", Color.WHITE).draw(game, 3, 9);
        new DockMessage("ORB (FOOD)", Color.WHITE).draw(game, 18, 9);
        new DockMessage("FIRE ORB", Color.WHITE).draw(game, 3, 11);
        new DockMessage("EARTH ORB", Color.WHITE).draw(game, 3, 13);
        new DockMessage("AIR ORB", Color.WHITE).draw(game, 3, 15);
        new DockMessage("ALMIGHTY ORB", Color.WHITE).draw(game, 3, 17);
        new DockMessage("CONTROLS:", Color.YELLOW).draw(game, 19);
        new DockMessage("↑ ↓ → ←       : DIRECTION", Color.WHITE).draw(game, 1, 21);
        new DockMessage("ENTER, L-CLICK: NEXT ELEMENT", Color.WHITE).draw(game, 1, 23);
        new DockMessage("ESC,   R-CLICK: PREV ELEMENT", Color.WHITE).draw(game, 1, 25);
        new DockMessage("SPACE         : NEW GAME", Color.WHITE).draw(game, 1, 27);
        new DockMessage("PRESS SPACE TO START", Color.PINK).draw(game, 30);
    }

    void displayGame() {
        Screen.set(Screen.Type.GAME);
        game.setTurnTimer(0);
        game.createGame();
    }
}
