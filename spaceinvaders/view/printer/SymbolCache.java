package com.javarush.games.spaceinvaders.view.printer;

import java.util.HashMap;
import java.util.Map;

/** Ленивый кэш глифов. */
public class SymbolCache {
    /** Символ-заглушка для неизвестных глифов. */
    private static final char FALLBACK = ' ';

    /** Кэш глифов по символам. */
    private final Map<Character, SymbolImage> glyphs = new HashMap<>(128);

    /** Возвращает глиф символа или пробел. */
    SymbolImage get(Character character) {
        if (glyphs.isEmpty()) {
            loadAllGlyphs();
        }

        SymbolImage glyph = glyphs.get(character);
        return (glyph != null) ? glyph : glyphs.get(FALLBACK);
    }

    /** Загружает все глифы в кэш. */
    private void loadAllGlyphs() {
        for (Symbol symbol : Symbol.values()) {
            for (char character : symbol.getChars()) {
                glyphs.put(character, new SymbolImage(symbol));
            }
        }
    }
}
