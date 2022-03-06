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
        loadFont();
    }

    // Images

    private static Image put(VisualElement element) {
        if (element.name().startsWith("SYM_")) return null; // Symbols are cached in another method
        Image result;
        if (element.name().startsWith("FLO_")) {
            result = new FloatingImage(element);
            images.put(element, result);
        } else {
            result = new Image(element);
            images.put(element, result);
        }
        return result;
    }

    public static Image get(VisualElement element) {
        Image image = images.get(element);
        if (image == null) image = put(element);
        return image;
    }

    // Buttons

    private static Button put(Button.ButtonID id) {
        Button button = new Button(id.x, id.y, id.width, id.height, id.label);
        buttons.put(id, button);
        return button;
    }

    public static Button get(Button.ButtonID id) {
        Button button = buttons.get(id);
        if (button == null) button = put(id);
        return button;
    }

    // Symbols

    private static void loadFont() {
        for (VisualElement element : VisualElement.values()) {
            if (element.name().startsWith("SYM_")) {
                for (char c : element.characters) {
                    symbols.put(c, new Image(element));
                }
            }
        }
    }

    public static Image get(Character c) {
        return symbols.get(c);
    }

    // Utility

    public static void refresh() {
        // Reload data for existing cache from Image Storage. Useful when changing themes.
        for (VisualElement element : images.keySet())
            put(element);
        for (Button.ButtonID id : buttons.keySet())
            put(id);
    }
}
