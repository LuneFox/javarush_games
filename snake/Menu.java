package com.javarush.games.snake;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.enums.Graphics;

class Menu {
    private SnakeGame game;

    Menu(SnakeGame game) {
        this.game = game;
    }

    void displayMain() {
        Screen.set(Screen.Type.MAIN_MENU);
        game.stopTurnTimer();
        game.createOrbsForMenu();
        game.drawScene();

        new Message("ALCHEMY SNAKE VER " + SnakeGame.VERSION, Color.LIGHTGREEN).draw(game);

        String selector1 = (Signs.currentSetting == Graphics.KANJI ? "■" : "□");
        String selector2 = (Signs.currentSetting == Graphics.EMOJI ? "■" : "□");
        new Message("SELECT ICONS: " + selector1 + " KANJI", Color.SKYBLUE).draw(game, 3);
        new Message(" (UP, DOWN)   " + selector2 + " EMOJI", Color.SKYBLUE).draw(game, 5);

        new Message("COLLECT THESE TO WIN:", Color.YELLOW).draw(game, 7);
        new Message("WATER ORB", Color.WHITE).draw(game, 3, 9);
        new Message("ORB (FOOD)", Color.WHITE).draw(game, 18, 9);
        new Message("FIRE ORB", Color.WHITE).draw(game, 3, 11);
        new Message("EARTH ORB", Color.WHITE).draw(game, 3, 13);
        new Message("AIR ORB", Color.WHITE).draw(game, 3, 15);
        new Message("ALMIGHTY ORB", Color.WHITE).draw(game, 3, 17);
        new Message("CONTROLS:", Color.YELLOW).draw(game, 19);
        new Message("↑ ↓ → ←       : DIRECTION", Color.WHITE).draw(game, 1, 21);
        new Message("ENTER, L-CLICK: NEXT ELEMENT", Color.WHITE).draw(game, 1, 23);
        new Message("ESC,   R-CLICK: PREV ELEMENT", Color.WHITE).draw(game, 1, 25);
        new Message("SPACE         : NEW GAME", Color.WHITE).draw(game, 1, 27);
        new Message("PRESS SPACE TO START", Color.PINK).draw(game, 30);
    }

    void displayGame() {
        Screen.set(Screen.Type.GAME);
        game.createGame();
    }
}
