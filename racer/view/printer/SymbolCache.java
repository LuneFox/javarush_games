package com.javarush.games.racer.view.printer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Lazy {@code char -> SymbolImage} cache used by the {@link Printer}. A miss rebuilds images for
 * every symbol in the font, after which the requested image is returned; characters shared by
 * several symbols overwrite one another in enum order. The map is pre-sized with a capacity hint
 * of 128.
 */
public class SymbolCache {
    private final Map<Character, SymbolImage> cache;

    /**
     * Creates an empty cache with an initial capacity hint of 128 entries.
     */
    SymbolCache() {
        cache = new HashMap<>(128);
    }

    /**
     * Looks up the glyph image for a character, filling the whole cache first if the character has
     * not been seen yet. A character with no glyph is never cached, so looking it up again rebuilds
     * the whole font each time and still returns null.
     *
     * @param character lowercased character to look up
     * @return the cached image, or null when no symbol declares this character
     */
    SymbolImage get(Character character) {
        SymbolImage value = cache.get(character);

        if (value == null) {
            value = put(character);
        }

        return value;
    }

    /**
     * Rebuilds cache entries for every symbol in the enum, then returns the requested one. Because
     * the whole font is inserted, later characters are cache hits; characters shared by several
     * symbols are overwritten in declaration order, so the last alias wins.
     *
     * @param character character whose image should be returned after the rebuild
     * @return the image for the character, or null if the font does not contain it
     */
    SymbolImage put(Character character) {
        Arrays.stream(Symbol.values()).forEach(element -> {
            for (char c : element.getChars()) {
                cache.put(c, new SymbolImage(element));
            }
        });

        return cache.get(character);
    }
}
