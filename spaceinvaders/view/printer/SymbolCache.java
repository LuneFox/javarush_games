package com.javarush.games.spaceinvaders.view.printer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SymbolCache {
    private final Map<Character, SymbolImage> cache;

    SymbolCache() {
        cache = new HashMap<>(128);
    }

    SymbolImage get(Character character) {
        SymbolImage value = cache.get(character);

        if (value == null) {
            value = put(character);
        }

        return value;
    }

    SymbolImage put(Character character) {
        Arrays.stream(Symbol.values()).forEach(element -> {
            for (char c : element.getChars()) {
                cache.put(c, new SymbolImage(element));
            }
        });

        return cache.get(character);
    }
}
