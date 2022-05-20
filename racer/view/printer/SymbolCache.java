package com.javarush.games.racer.view.printer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SymbolCache {
    private final Map<Character, SymbolImage> cache;

    SymbolCache(int size) {
        cache = new HashMap<>(size);
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
