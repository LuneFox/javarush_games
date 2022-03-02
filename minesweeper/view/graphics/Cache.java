package com.javarush.games.minesweeper.view.graphics;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for storing permanent elements that are often called. No need to create new instances every time.
 */

public class Cache {
    private static final Map<VisualElement, Image> images = new EnumMap<>(VisualElement.class);
    private static final Map<Button.ButtonID, Button> buttons = new EnumMap<>(Button.ButtonID.class);
    private static final Map<Character, Image> symbols = new HashMap<>(128);

    static {
        fill();
    }

    public static void fill() {
        // Update maps with fresh data (generate all elements again). Useful when changing themes.
        for (VisualElement element : VisualElement.values()) {
            put(element);
        }
        for (Button.ButtonID id : Button.ButtonID.values()) {
            put(id);
        }
    }

    // Images

    private static void put(VisualElement element) {
        if (element.name().startsWith("FLO_")) {
            images.put(element, new FloatingImage(element));
        } else if (element.name().startsWith("SYM_")) {
            Image symbol = new Image(element);
            for (char c : element.characters) symbols.put(c, symbol);
            images.put(element, symbol);
        } else {
            images.put(element, new Image(element));
        }
    }

    public static Image get(VisualElement element) {
        return images.get(element);
    }

    // Buttons

    private static void put(Button.ButtonID id) {
        buttons.put(id, new Button(id.posX, id.posY, id.width, id.height, id.label));
    }

    public static Button get(Button.ButtonID id) {
        return buttons.get(id);
    }

    // Symbols

    public static Image get(Character c) {
        return symbols.get(c);
    }
}
