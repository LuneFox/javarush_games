package com.javarush.games.minesweeper.gui;

import java.util.HashMap;
import java.util.Map;

/** Простой кэш значений по ключу. */
public abstract class Cache<K, V> {
    /** Кэш значений. */
    protected final Map<K, V> cache;

    /** Создаёт экземпляр Cache. */
    public Cache(int size) {
        cache = new HashMap<>(size);
    }

    /** Возвращает значение из кэша. */
    public V get(K key) {
        V value = cache.get(key);

        if (value == null) {
            value = put(key);
        }

        return value;
    }

    /**
     * Возвращает значение из кэша без создания нового.
     */
    protected V getIfPresent(K key) {
        return cache.get(key);
    }

    /** Создаёт и сохраняет значение. */
    protected abstract V put(K key);
}
