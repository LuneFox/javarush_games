package com.javarush.games.snake;

import java.util.ArrayList;

public class Screen {
    private Type type;
    private static ArrayList<Screen> screens = new ArrayList<>();

    public enum Type {
        MAIN_MENU, GAME, OPTIONS, CONTROLS, HELP, MAP_EDIT
    }

    static {
        screens.add(new Screen(Type.MAIN_MENU));
        screens.add(new Screen(Type.GAME));
        screens.add(new Screen(Type.OPTIONS));
        screens.add(new Screen(Type.CONTROLS));
        screens.add(new Screen(Type.HELP));
        screens.add(new Screen(Type.MAP_EDIT));
    }

    private Screen(Type type) {
        this.type = type;
    }

    public static void set(Type type) { // bringing active screen to the top of the list
        Screen screen = null;
        for (Screen s : screens) {
            if (s.type == type) {
                screen = s;
            }
        }
        screens.remove(screen);
        screens.add(0, screen);
    }

    public static Type get() { // returns current active screen type
        return screens.get(0).type;
    }

    public static boolean is(Type type){
        return (screens.get(0).type == type);
    }
}
