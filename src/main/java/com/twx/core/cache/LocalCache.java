package com.twx.core.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * 想法：此cache用来缓存方法内的对象。作用域只在方法内。
 * 场景：for循环，可能多次调用相同的接口获取数据，用此来缓存数据，达到时间的优化
 * Created by vincent.tong on 2016/12/12.
 */
public class LocalCache<K, V> {
    private Map<K, V> cache = new HashMap<>();
    private Function<K, V> defaultGetter;

    public LocalCache(Function<K, V> getter) {
        setDefaultGetter(getter);
    }

    public void setDefaultGetter(Function<K, V> getter) {
        Objects.requireNonNull(getter);
        defaultGetter = getter;
    }

    public V get(K key) {
        return get(key, defaultGetter);
    }

    public V get(K key, Function<K, V> getter) {
        Objects.requireNonNull(getter);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        V value = getter.apply(key);
        cache.put(key, value);
        return value;
    }

    public V remove(K key) {
        return cache.remove(key);
    }
}
