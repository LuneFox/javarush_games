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
            putAllSymbols();
            value = cache.get(character);
        }

        // Characters that have no glyph are printed as blank space, the whole line must not break
        return (value != null) ? value : cache.get(' ');
    }

    private void putAllSymbols() {
        Arrays.stream(Symbol.values()).forEach(element -> {
            for (char c : element.getChars()) {
                cache.put(c, new SymbolImage(element));
            }
        });
    }
}
