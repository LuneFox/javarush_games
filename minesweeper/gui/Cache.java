package com.javarush.games.minesweeper.gui;

import com.javarush.games.minesweeper.gui.image.FloatingImage;
import com.javarush.games.minesweeper.gui.image.Image;
import com.javarush.games.minesweeper.gui.image.ImageID;
import com.javarush.games.minesweeper.gui.interactive.Button;
import com.javarush.games.minesweeper.gui.interactive.ButtonID;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for storing permanent elements that are often called. No need to create new instances every time.
 */

public class Cache {
    private static final Map<ImageID, Image> IMAGES = new EnumMap<>(ImageID.class);
    private static final Map<ButtonID, Button> BUTTONS = new EnumMap<>(ButtonID.class);
    private static final Map<Character, Image> SYMBOLS = new HashMap<>(128);

    static {
        loadFont();
    }

    // Images

    private static Image put(ImageID element) {
        Image result;
        if (element.name().startsWith("FLO_")) {
            result = new FloatingImage(element);
            IMAGES.put(element, result);
        } else {
            result = new Image(element);
            IMAGES.put(element, result);
        }
        return result;
    }

    public static Image get(ImageID element) {
        Image image = IMAGES.get(element);
        if (image == null) image = put(element);
        return image;
    }

    // Buttons

    private static Button put(ButtonID id) {
        Button button = new Button(id.x, id.y, id.width, id.height, id.label);
        BUTTONS.put(id, button);
        return button;
    }

    public static Button get(ButtonID id) {
        Button button = BUTTONS.get(id);
        if (button == null) button = put(id);
        return button;
    }

    // Symbols

    private static void loadFont() {
        Arrays.stream(ImageID.values())
                .filter(element -> element.name().startsWith("SYM_"))
                .forEach(element -> {
                    for (char c : element.getCharacters()) {
                        SYMBOLS.put(c, new Image(element));
                    }
                });
    }

    public static Image get(Character c) {
        return SYMBOLS.get(c);
    }

    // Utility

    public static void refresh() { // Reload existing cache data from Image Storage. Useful when changing themes.
        IMAGES.keySet().forEach(Cache::put);
        BUTTONS.keySet().forEach(Cache::put);
    }
}
