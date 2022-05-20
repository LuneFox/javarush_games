package com.javarush.games.racer.view.printer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SymbolCache {
    private final Map<Character, SymbolImage> cache;

    SymbolCache(int size) {
        cache = new HashMap<>(size);
    }

    SymbolImage get(Character key) {
        SymbolImage value = cache.get(key);

        if (value == null) {
            value = put(key);
        }

        return value;
    }

    SymbolImage put(Character key) {
        Arrays.stream(Symbol.values()).forEach(element -> {
            for (char c : element.getCharacters()) {
                cache.put(c, new SymbolImage(element));
            }
        });

        return cache.get(key);
    }
}
