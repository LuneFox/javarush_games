package com.javarush.games.snake;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.enums.Element;
import com.javarush.games.snake.enums.Graphics;

import java.util.ArrayList;
import java.util.List;

class Menu {
    private SnakeGame game;
    ArrayList<HelpPage> helpPages = new ArrayList<>();
    int lastPointerPosition;
    int currentHelpPage;

    Menu(SnakeGame game) {
        this.game = game;
        Selector.getInstance(game);
    }

    private int brush;

    // DISPLAY SCREENS

    void displayMain() {
        Screen.set(Screen.Type.MAIN_MENU);
        drawBlackBackground();
        new Message(-1, 5, "✡                     ✡", Color.YELLOW).draw();
        new Message(-1, 5, "✡                   ✡", Color.CYAN).draw();
        new Message(-1, 5, "✡                 ✡", Color.LAWNGREEN).draw();
        new Message(-1, 5, "✡               ✡", Color.RED).draw();
        new Message(-1, 5, "ALCHEMY SNAKE", Color.FUCHSIA).draw();
        new Message(-1, 7, "MASTER OF ELEMENTS", Color.PINK).draw();
        new Message(-1, 30, "VER " + Strings.VERSION, Color.BLUE).draw();
        Selector.setEntries("START", "OPTIONS", "CONTROLS", "HELP");
        // Selector.setEntries("START", "OPTIONS", "CONTROLS", "HELP", "EDIT");
        Selector.draw(13, 12);
        currentHelpPage = 0;
    }

    void displayOptions() {
        drawBlackBackground();
        Screen.set(Screen.Type.OPTIONS);
        new Message(-1, 7, "OPTIONS", Color.SKYBLUE).draw();
        Selector.setEntries("MAP", "SYMBOLS", "ACCELERATION");
        Selector.draw(2, 12);
        new Message(17, 12, "STAGE " + (game.getStage() + 1), Color.WHITE).draw();
        new Message(17, 14, Signs.currentSetting.toString(), Color.WHITE).draw();
        new Orb(23, 14, Element.WATER).draw(game);
        new Message(17, 16, (game.acceleration) ? "ENABLED" : "DISABLED", Color.WHITE).draw();
    }

    void displayControls() {
        drawBlackBackground();
        Screen.set(Screen.Type.CONTROLS);
        HelpPage.getControls().draw(game);
    }

    void displayHelp() {
        helpPages.clear();
        helpPages.add(HelpPage.getGoals());
        helpPages.add(HelpPage.getSnakeAbilities());
        helpPages.add(HelpPage.getTypesOfTerrain());
        helpPages.add(HelpPage.getTips1());
        helpPages.add(HelpPage.getTips2());
        drawBlackBackground();
        Screen.set(Screen.Type.HELP);
        helpPages.get(currentHelpPage).draw(game);
        new Message(-1, 30, "← PAGE " + (currentHelpPage + 1) + "/" + helpPages.size() + " →", Color.WHITE).draw();
    }

    void nextHelpPage() {
        if (currentHelpPage + 1 < helpPages.size()) {
            currentHelpPage++;
            displayHelp();
        }
    }

    void previousHelpPage() {
        if (currentHelpPage > 0) {
            currentHelpPage--;
            displayHelp();
        }
    }

    void startGame() {
        Screen.set(Screen.Type.GAME);
        game.createGame();
    }


    // MAP EDITOR (COMMENT PRINT LINES OUT BEFORE UPLOADING TO JAVARUSH)

    void displayMapEditor() {
        Screen.set(Screen.Type.MAP_EDIT);
        Node node = new Node(1, 1, game, brush);
        game.getMap().setLayoutNode(1, 1, node.getTerrain());
        game.drawMap();
        new Message(3, 1, node.getTerrain().name(), Color.WHITE).draw();
    }

    void brushNext() {
        if (brush < 9) {
            brush++;
        } else {
            brush = 0;
        }
    }

    void brushPrevious() {
        if (brush > 0) {
            brush--;
        } else {
            brush = 9;
        }
    }

    void drawTerrain(int x, int y) {
        Node node = new Node(x, y, game, brush);
        game.getMap().setLayoutNode(node.x, node.y, node.getTerrain());
    }

    void copyTerrain(int x, int y) {
        brush = game.getMap().getLayoutNode(x, y).getTerrain().ordinal();
    }

    void printTerrain() {
/*        Map map = game.getMap();
        System.out.println("new int[][]{");

        for (int y = 0; y < SnakeGame.HEIGHT; y++) {
            for (int x = 0; x < SnakeGame.WIDTH; x++) {
                int number = (y < 4 ? 9 : map.getLayoutNode(x, y).getTerrain().ordinal());
                if (x == 0) {
                    System.out.print("{");
                }
                if (x < SnakeGame.WIDTH - 1) {
                    System.out.print(number + ", ");
                }
                if (x == SnakeGame.WIDTH - 1) {
                    System.out.print(number);
                    System.out.println("},");
                }
            }
        }
        System.out.println("});");*/
    }

    void printCoordinate(int x, int y) {
        // System.out.println(x + "," + y);
    }


    // UTILITIES

    void selectStageUp() {
        if (game.getStage() < Map.stages.size() - 2) {
            game.setStage(game.getStage() + 1);
        } else {
            game.setStage(0);
        }
    }

    void selectStageDown() {
        if (game.getStage() > 0) {
            game.setStage(game.getStage() - 1);
        } else {
            game.setStage(Map.stages.size() - 2);
        }
    }

    void drawBlackBackground() {
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


    // INNER CLASSES

    public static class Selector {
        SnakeGame game;
        ArrayList<String> entries;
        static Selector instance;
        int pointer;
        private final static String POINTER_SIGN = "→";


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
                    instance.game.setCellValueEx(x - 2, y, Color.NONE, POINTER_SIGN, Color.YELLOW, 90);
                } else {
                    instance.game.setCellValue(x - 2, y, "");
                }
                Message line = new Message(x, y, instance.entries.get(i), (instance.pointer == i ? Color.WHITE : Color.GRAY));
                line.draw();
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
