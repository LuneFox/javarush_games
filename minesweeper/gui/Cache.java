package com.javarush.games.minesweeper.gui;

import java.util.HashMap;
import java.util.Map;

/** Simple cache realization. Returns value when something is put into inner hashmap.
 *
 * @param <K> key
 * @param <V> value
 */

public abstract class Cache<K, V> {
    protected final Map<K, V> cache;

    public Cache(int size) {
        cache = new HashMap<>(size);
    }

    public V get(K key) {
        V value = cache.get(key);
        if (value == null) {
            value = put(key);
        }
        return value;
    }

    protected abstract V put(K key);
}
