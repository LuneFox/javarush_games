package com.javarush.games.ticktacktoe.view.printer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Кэш для повторного использования символов.
 *
 * @author LuneFox
 */
public class SymbolCache {
    private final Map<Character, SymbolImage> cache;

    SymbolCache() {
        cache = new HashMap<>(128);
    }

    /**
     * Получить изображение символа из кэша. Если такой не был кэширован, сначала добавить его в кэш, а потом вернуть.
     *
     * @param character печатаемый символ
     * @return изображение символа из кэша
     */
    SymbolImage get(Character character) {
        SymbolImage value = cache.get(character);

        if (value == null) {
            value = put(character);
        }

        return value;
    }

    /**
     * Добавить изображение символа в кэш.
     *
     * @param character печатаемый символ
     * @return изображение добавленного в кэш символа
     */
    private SymbolImage put(Character character) {
        Arrays.stream(Symbol.values()).forEach(element -> {
            for (char c : element.getChars()) {
                cache.put(c, new SymbolImage(element));
            }
        });

        return cache.get(character);
    }
}
