package com.javarush.games.racer.view.printer;

import java.util.HashMap;
import java.util.Map;

/** Simple cache realization. Returns value when something is put into inner hashmap.
 *
 * @param <K> key
 * @param <V> value
 */
abstract class SimpleCache<K, V> {
    protected final Map<K, V> cache;

    public SimpleCache(int size) {
        cache = new HashMap<>(size);
    }

    public V get(K key) {
        V value = cache.get(key);
        if (value == null) {
            value = put(key);
        }
        return value;
    }

    abstract V put(K key);
}
