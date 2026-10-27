package com.javarush.games.snake.view;

import com.javarush.engine.cell.Color;
import com.javarush.games.snake.SnakeGame;
import com.javarush.games.snake.model.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Указатель и пункты меню. */
public class MenuSelector {
    private static final SnakeGame game = SnakeGame.getInstance();
    private static final MenuSelector instance = new MenuSelector();
    private static final String POINTER_SIGN = "→";
    private static final int POINTER_TEXT_SIZE = 90;
    private static final int POINTER_OFFSET = 2;
    private static final int ENTRY_SPACING = 2;

    private final List<Strings> menuEntries;
    private int pointerPosition;
    private int lastPointerPosition;

    /** Создаёт пустой список пунктов. */
    private MenuSelector() {
        menuEntries = new ArrayList<>();
    }

    /** Рисует пункты меню с указателем. */
    public static void drawMenuEntriesWithPointer(int x, int y) {
        for (int position = 0; position < instance.menuEntries.size(); position++) {
            drawEntry(x, y, position);
            drawPointer(x, y, position);
            y += ENTRY_SPACING;
        }
    }

    /** Рисует один пункт меню. */
    private static void drawEntry(int x, int y, int position) {
        final String text = instance.menuEntries.get(position).get();
        final Color color = instance.pointerPosition == position ? Color.WHITE : Color.GRAY;
        Message.print(x, y, text, color);
    }

    /** Рисует указатель у пункта. */
    private static void drawPointer(int x, int y, int position) {
        final int pointerX = x - POINTER_OFFSET;

        if (instance.pointerPosition == position) {
            game.setCellValueEx(pointerX, y, Color.NONE, POINTER_SIGN, Color.YELLOW, POINTER_TEXT_SIZE);
        } else {
            game.setCellValue(pointerX, y, "");
        }
    }

    /** Двигает указатель вниз. */
    public static void movePointerDown() {
        if (instance.pointerPosition >= instance.menuEntries.size() - 1) return;
        instance.pointerPosition++;
    }

    /** Двигает указатель вверх. */
    public static void movePointerUp() {
        if (instance.pointerPosition <= 0) return;
        instance.pointerPosition--;
    }

    /** Выбран ли указанный пункт. */
    public static boolean isPointingAt(Strings option) {
        final Strings selectedOption = instance.menuEntries.get(instance.pointerPosition);
        return option == selectedOption;
    }

    /** Задаёт пункты меню. */
    public static void setMenuEntries(Strings... entries) {
        instance.menuEntries.clear();
        Collections.addAll(instance.menuEntries, entries);
    }

    /** Запоминает позицию указателя. */
    public static void saveLastPointerPosition() {
        instance.lastPointerPosition = instance.pointerPosition;
    }

    /** Восстанавливает позицию указателя. */
    public static void loadLastPointerPosition() {
        instance.pointerPosition = instance.lastPointerPosition;
    }

    /** Ставит указатель в позицию. */
    public static void setPointerPosition(int position) {
        instance.pointerPosition = position;
    }
}
