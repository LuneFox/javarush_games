package com.javarush.games.spaceinvaders.view.printer;

import java.util.HashMap;
import java.util.Map;

/**
 * Lazily built set of glyphs. A character that has no glyph is printed as a space, so a single
 * unsupported symbol never breaks the whole line.
 */
public class SymbolCache {
    private static final char FALLBACK = ' ';

    private final Map<Character, SymbolImage> glyphs = new HashMap<>(128);

    SymbolImage get(Character character) {
        if (glyphs.isEmpty()) {
            loadAllGlyphs();
        }

        SymbolImage glyph = glyphs.get(character);
        return (glyph != null) ? glyph : glyphs.get(FALLBACK);
    }

    private void loadAllGlyphs() {
        for (Symbol symbol : Symbol.values()) {
            for (char character : symbol.getChars()) {
                glyphs.put(character, new SymbolImage(symbol));
            }
        }
    }
}
