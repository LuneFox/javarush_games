package com.javarush.games.minesweeper.model;

import java.util.HashMap;
import java.util.Map;

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

    public void refresh() {
        cache.keySet().forEach(this::put);
    }
}
