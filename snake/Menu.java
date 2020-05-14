package com.javarush.games.snake;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.enums.Element;
import com.javarush.games.snake.enums.Graphics;

import java.util.ArrayList;

class Menu {
    private SnakeGame game;
    int lastPointerPosition;

    Menu(SnakeGame game) {
        this.game = game;
        Selector.getInstance(game);
    }

    // DISPLAY SCREENS

    void displayMain() {
        Screen.set(Screen.Type.MAIN_MENU);
        drawBackground();
        new Message("ALCHEMY SNAKE", Color.LIGHTGREEN).draw(game, 5);
        new Message("VER " + Strings.VERSION, Color.DARKBLUE).draw(game, 30);

        Selector.setEntries("START", "OPTIONS", "CONTROLS", "HELP");
        Selector.draw(13, 12);
    }

    void displayOptions() {
        drawBackground();
        Screen.set(Screen.Type.OPTIONS);
        new Message("OPTIONS", Color.SKYBLUE).draw(game, 7);
        Selector.setEntries("MAP", "SYMBOLS");
        Selector.draw(2, 12);

        new Message("STAGE " + (game.getStage() + 1), Color.WHITE).draw(game, 15, 12);

        new Message(Signs.currentSetting.toString(), Color.WHITE).draw(game, 15, 14);
        new Orb(21, 14, Element.WATER).draw(game);

    }

    void displayControls() {
        drawBackground();
        Screen.set(Screen.Type.CONTROLS);
        new Message("CONTROLS", Color.SKYBLUE).draw(game, 7);
        new Message("↑ ↓ → ←       :", Color.YELLOW).draw(game, 1, 11);
        new Message("HOLD DIRECTION:", Color.YELLOW).draw(game, 1, 13);
        new Message("ENTER, L-CLICK:", Color.YELLOW).draw(game, 1, 15);
        new Message("ESC,   R-CLICK:", Color.YELLOW).draw(game, 1, 17);
        new Message("SPACE         :", Color.YELLOW).draw(game, 1, 21);
        new Message("DIRECTION", Color.WHITE).draw(game, 17, 11);
        new Message("ACCELERATE", Color.WHITE).draw(game, 17, 13);
        new Message("NEXT ELEMENT", Color.WHITE).draw(game, 17, 15);
        new Message("PREV ELEMENT", Color.WHITE).draw(game, 17, 17);
        new Message("BACK TO MENU", Color.WHITE).draw(game, 17, 21);
        new Message("AT GAME OVER", Color.WHITE).draw(game, 17, 23);
    }

    void displayHelp() {
        drawBackground();
        Screen.set(Screen.Type.HELP);
        new Message("HELP", Color.SKYBLUE).draw(game, 7);
        new Message("COLLECT TO WIN:", Color.YELLOW).draw(game, 9);
        new Message("ORB OF WATER", Color.LIGHTBLUE).draw(game, 3, 11);
        new Message("ORB OF FIRE", Color.RED).draw(game, 3, 13);
        new Message("ORB OF EARTH", Color.ORANGE).draw(game, 3, 15);
        new Message("ORB OF AIR", Color.AZURE).draw(game, 3, 17);
        new Message("ORB OF POWER", Color.PINK).draw(game, 3, 19);
        new Message("COLLECT TO GROW:", Color.YELLOW).draw(game, 21);
        new Message("ORB OF WISDOM", Color.MEDIUMPURPLE).draw(game, 3, 23);

        new Orb(1, 11, Element.WATER).draw(game);
        new Orb(1, 13, Element.FIRE).draw(game);
        new Orb(1, 15, Element.EARTH).draw(game);
        new Orb(1, 17, Element.AIR).draw(game);
        new Orb(1, 19, Element.ALMIGHTY).draw(game);
        new Orb(1, 23, Element.NEUTRAL).draw(game);
    }

    void startGame() {
        Screen.set(Screen.Type.GAME);
        game.createGame();
    }

    // UTILITIES

    void drawBackground() {
        for (int x = 0; x < SnakeGame.WIDTH; x++) {
            for (int y = 0; y < SnakeGame.HEIGHT; y++) {
                game.setCellValueEx(x, y, Color.BLACK, "");
            }
        }
    }

    void switchSymbolSet() {
        if (Signs.currentSetting == Graphics.KANJI) {
            Signs.set(Graphics.EMOJI);
        } else {
            Signs.set(Graphics.KANJI);
        }
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
                    instance.game.setCellValueEx(x - 2, y, Color.NONE, ">", Color.YELLOW, 90);
                } else {
                    instance.game.setCellValue(x - 2, y, "");
                }
                Message line = new Message(instance.entries.get(i), (instance.pointer == i ? Color.WHITE : Color.GRAY));
                line.draw(instance.game, x, y);
                y += 2;
            }
        }

        static void selectDown() {
            if (instance.pointer < instance.entries.size() - 1) {
                instance.pointer++;
            }
        }

        static void selectUp() {
            if (instance.pointer > 0) {
                instance.pointer--;
            }
        }

        // MECHANICS

        static boolean nowAt(String option) {
            return (option.equals(instance.entries.get(instance.pointer)));
        }


        // GETTERS

        static Selector getInstance(SnakeGame game) {
            if (instance == null) {
                instance = new Selector(game);
            }
            return instance;
        }

        public static int getPointer() {
            return instance.pointer;
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
