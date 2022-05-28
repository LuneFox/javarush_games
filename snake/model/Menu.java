package com.javarush.games.snake.model;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.enums.Element;
import com.javarush.games.snake.view.Message;
import com.javarush.games.snake.view.Phase;
import com.javarush.games.snake.view.Sign;
import com.javarush.games.snake.view.SignType;

import java.util.ArrayList;

public class Menu {
    private SnakeGame game;
    ArrayList<HelpPage> helpPages = new ArrayList<>();
    public int lastPointerPosition;
    int currentHelpPage;

    public Menu(SnakeGame game) {
        this.game = game;
        Selector.getInstance(game);
    }

    private int brush;

    // DISPLAY SCREENS

    public void displayMain() {
        Phase.set(Phase.MAIN_MENU);
        drawBlackBackground();
        Message.print(-1, 5, "✡                     ✡", Color.YELLOW);
        Message.print(-1, 5, "✡                   ✡", Color.CYAN);
        Message.print(-1, 5, "✡                 ✡", Color.LAWNGREEN);
        Message.print(-1, 5, "✡               ✡", Color.RED);
        Message.print(-1, 5, "ALCHEMY SNAKE", Color.FUCHSIA);
        Message.print(-1, 7, "MASTER OF ELEMENTS", Color.PINK);
        Message.print(-1, 30, "VER " + Strings.VERSION, Color.BLUE);
        Selector.setEntries("START", "OPTIONS", "CONTROLS", "HELP");
        // Selector.setEntries("START", "OPTIONS", "CONTROLS", "HELP", "EDIT");
        Selector.draw(13, 12);
        currentHelpPage = 0;
    }

    public void displayOptions() {
        drawBlackBackground();
        Phase.set(Phase.OPTIONS);
        Message.print(-1, 7, "OPTIONS", Color.SKYBLUE);
        Selector.setEntries("MAP", "SYMBOLS", "ACCELERATION");
        Selector.draw(2, 12);
        Message.print(17, 12, "STAGE " + (game.getStage() + 1), Color.WHITE);
        Message.print(17, 14, Sign.getUsedType().toString(), Color.WHITE);
        new Orb(23, 14, Element.WATER).draw(game);
        Message.print(17, 16, (game.acceleration) ? "ENABLED" : "DISABLED", Color.WHITE);
    }

    public void displayControls() {
        drawBlackBackground();
        Phase.set(Phase.CONTROLS);
        HelpPage.getControls().draw(game);
    }

    public void displayHelp() {
        helpPages.clear();
        helpPages.add(HelpPage.getGoals());
        helpPages.add(HelpPage.getSnakeAbilities());
        helpPages.add(HelpPage.getTypesOfTerrain());
        helpPages.add(HelpPage.getTips1());
        helpPages.add(HelpPage.getTips2());
        drawBlackBackground();
        Phase.set(Phase.HELP);
        helpPages.get(currentHelpPage).draw(game);
        Message.print(-1, 30, "← PAGE " + (currentHelpPage + 1) + "/" + helpPages.size() + " →", Color.WHITE);
    }

    public void nextHelpPage() {
        if (currentHelpPage + 1 < helpPages.size()) {
            currentHelpPage++;
            displayHelp();
        }
    }

    public void previousHelpPage() {
        if (currentHelpPage > 0) {
            currentHelpPage--;
            displayHelp();
        }
    }

    public void startGame() {
        Phase.set(Phase.GAME);
        game.createGame();
    }


    // MAP EDITOR (COMMENT PRINT LINES OUT BEFORE UPLOADING TO JAVARUSH)

    public void displayMapEditor() {
        Phase.set(Phase.MAP_EDIT);
        Node node = new Node(1, 1, game, brush);
        game.getMap().setLayoutNode(1, 1, node.getTerrain());
        game.drawMap();
        Message.print(3, 1, node.getTerrain().name(), Color.WHITE);
    }

    public void brushNext() {
        if (brush < 9) {
            brush++;
        } else {
            brush = 0;
        }
    }

    public void brushPrevious() {
        if (brush > 0) {
            brush--;
        } else {
            brush = 9;
        }
    }

    public void drawTerrain(int x, int y) {
        Node node = new Node(x, y, game, brush);
        game.getMap().setLayoutNode(node.x, node.y, node.getTerrain());
    }

    public void copyTerrain(int x, int y) {
        brush = game.getMap().getLayoutNode(x, y).getTerrain().ordinal();
    }

    public void printTerrain() {
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

    public void printCoordinate(int x, int y) {
        // System.out.println(x + "," + y);
    }


    // UTILITIES

    public void selectStageUp() {
        if (game.getStage() < Map.stages.size() - 2) {
            game.setStage(game.getStage() + 1);
        } else {
            game.setStage(0);
        }
    }

    public void selectStageDown() {
        if (game.getStage() > 0) {
            game.setStage(game.getStage() - 1);
        } else {
            game.setStage(Map.stages.size() - 2);
        }
    }

    void drawBlackBackground() {
        for (int x = 0; x < SnakeGame.SIZE; x++) {
            for (int y = 0; y < SnakeGame.SIZE; y++) {
                game.setCellValueEx(x, y, Color.BLACK, "");
            }
        }
    }

    public void switchSymbolSet() {
        Sign.setUsedType(Sign.getUsedType() == SignType.KANJI ? SignType.EMOJI : SignType.KANJI);
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
                Message.print(x, y, instance.entries.get(i), (instance.pointer == i ? Color.WHITE : Color.GRAY));
                y += 2;
            }
        }

        public static void selectDown() {
            if (instance.pointer < instance.entries.size() - 1) {
                instance.pointer++;
            }
        }

        public static void selectUp() {
            if (instance.pointer > 0) {
                instance.pointer--;
            }
        }

        // MECHANICS

        public static boolean nowAt(String option) {
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

        public static void setPointer(int position) {
            instance.pointer = position;
        }
    }
}
