package com.javarush.games.snake;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.enums.Graphics;

import java.nio.channels.Selector;
import java.util.ArrayList;

class Menu {
    private SnakeGame game;

    Menu(SnakeGame game) {
        this.game = game;
        Selector.getInstance(game);
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

    void displayNewMain() {
        Screen.set(Screen.Type.MAIN_MENU);
        new Message("ALCHEMY SNAKE", Color.LIGHTGREEN).draw(game, 5);
        new Message("VER." + SnakeGame.VERSION, Color.GRAY).draw(game, 30);

        Selector.setEntries("START", "OPTIONS", "CONTROLS", "HELP");
        Selector.draw(13, 12);
    }

    void displayGame() {
        Screen.set(Screen.Type.GAME);
        game.createGame();
    }

    public static class Selector {
        SnakeGame game;
        ArrayList<String> entries;
        static Selector instance;
        int pointer;


        // CONSTRUCTOR

        private Selector(SnakeGame game) {
            this.game = game;
            entries = new ArrayList<>();
            this.pointer = 0;
        }


        // VISUALS

        public static void draw(int x, int y) {
            for (int i = 0; i < instance.entries.size(); i++) {
                if (instance.pointer == i) {
                    instance.game.setCellValueEx(x - 2, y, Color.NONE, ">>", Color.YELLOW);
                } else {
                    instance.game.setCellValue(x - 2, y, "");
                }
                Message line = new Message(instance.entries.get(i), (instance.pointer == i ? Color.YELLOW : Color.WHITE));
                line.draw(instance.game, x, y);
                y += 2;
            }
        }

        static void selectDown() {
            if (instance.pointer < instance.entries.size() - 1) {
                System.out.println(instance.pointer);
                instance.pointer++;
            }
        }

        static void selectUp() {
            if (instance.pointer > 0) {
                System.out.println(instance.pointer);
                instance.pointer--;
            }
        }


        // GETTERS

        static Selector getInstance(SnakeGame game) {
            if (instance == null) {
                instance = new Selector(game);
            }
            return instance;
        }

        static String getCurrentOption() {
            return instance.entries.get(instance.pointer);
        }


        // SETTERS

        static void setEntries(String... strings) {
            instance.entries.clear();
            for (String s : strings) {
                instance.entries.add(s);
            }
        }

        static void setPointer(int position) {
            instance.pointer = position;
        }
    }
}
