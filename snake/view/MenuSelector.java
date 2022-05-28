package com.javarush.games.snake.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;

import java.util.ArrayList;
import java.util.Collections;

public class MenuSelector {
    private static final SnakeGame game = SnakeGame.getInstance();
    private static final MenuSelector instance;
    private static final String POINTER_SIGN = "→";

    private final ArrayList<String> menuEntries;
    private int pointerPosition;
    private int lastPointerPosition;

    static {
        instance = new MenuSelector();
    }

    private MenuSelector() {
        menuEntries = new ArrayList<>();
        pointerPosition = 0;
    }

    public static void drawMenuEntriesWithPointer(int x, int y) {
        for (int position = 0; position < instance.menuEntries.size(); position++) {
            drawEntry(x, y, position);
            drawPointer(x, y, position);
            y += 2;
        }
    }

    private static void drawEntry(int x, int y, int position) {
        final String text = instance.menuEntries.get(position);
        final Color color = instance.pointerPosition == position ? Color.WHITE : Color.GRAY;
        Message.print(x, y, text, color);
    }

    private static void drawPointer(int x, int y, int position) {
        x -= 2;

        if (instance.pointerPosition == position) {
            game.setCellValueEx(x, y, Color.NONE, POINTER_SIGN, Color.YELLOW, 90);
        } else {
            game.setCellValue(x, y, "");
        }
    }

    public static void movePointerDown() {
        if (instance.pointerPosition >= instance.menuEntries.size() - 1) return;
        instance.pointerPosition++;
    }

    public static void movePointerUp() {
        if (instance.pointerPosition <= 0) return;
        instance.pointerPosition--;
    }

    public static boolean isPointingAt(String option) {
        String currentlySelectedOption = instance.menuEntries.get(instance.pointerPosition);
        return (option.equalsIgnoreCase(currentlySelectedOption));
    }

    public static void setMenuEntries(String... strings) {
        instance.menuEntries.clear();
        Collections.addAll(instance.menuEntries, strings);
    }

    public static void saveLastPointerPosition() {
        instance.lastPointerPosition = instance.pointerPosition;
    }

    public static void loadLastPointerPosition() {
        instance.pointerPosition = instance.lastPointerPosition;
    }

    public static void setPointerPosition(int position) {
        instance.pointerPosition = position;
    }
}
