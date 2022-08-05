package ru.instamart.redis;

import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class RedisService {
    
    /**
     * Переименование value по ключу
     * @param key
     * @param newValue
     */
    public static synchronized void update(final JedisPool pool, final String key, final String newValue) {
        try (final var client = pool.getResource()) {
            client.append(key, newValue);
        }
    }

    /**
     *
     * @param fromKey
     * @param toKey
     * @return - переименование ключа
     */
    public static synchronized void rename(final JedisPool pool, final String fromKey, final String toKey) {
        try (final var client = pool.getResource()) {
            client.rename(fromKey, toKey);
        }
    }

    /**
     *
     * @param key
     * @return - проверка на существование ключа
     */
    public static synchronized boolean isExist(final JedisPool pool, final String key) {
        try (final var client = pool.getResource()) {
            return client.exists(key);
        }
    }

    /**
     *
     * @param key
     * @return - тип хранимого значения
     */
    public static synchronized String getType(final JedisPool pool, final String key) {
        try (final var client = pool.getResource()) {
            return client.type(key);
        }
    }

    /**
     *
     * @return - получить список всех ключей
     */
    public static synchronized  Set<String> getAll(final JedisPool pool) {
        try (final var client = pool.getResource()) {
            return client.keys("*");
        }
    }

    /**
     *
     * @param key
     * @return - получить value по ключу
     */
    public static synchronized String get(final JedisPool pool, final String key) {
        try (final var client = pool.getResource()) {
            return client.get(key);
        }
    }

    /**
     * Отправка батчем(одним большим упакованным запросом, вместо кучи мелких)
     * @param data
     */
    public static synchronized void set(final JedisPool pool, final Map<String, String> data) {
        try (final var client = pool.getResource()) {
            final var p = client.pipelined();
            data.forEach(p::set);
            p.sync();
        }
    }

    /**
     * @param key
     * @return - Получить TTL для ключа в секундах
     */
    public static synchronized long getTtl(final JedisPool pool, final String key) {
        try (final var client = pool.getResource()) {
            return client.ttl(key);
        }
    }

    /**
     * Установить TTL для ключа
     * @param key
     * @param ttl - время в секундах
     */
    public static synchronized void setTtl(final JedisPool pool, final String key, final int ttl) {
        try (final var client = pool.getResource()) {
            client.expire(key, ttl);
        }
    }

    /**
     * Удалить TTL
     * @param key - имя
     */
    public static synchronized void removeTtl(final JedisPool pool, final String key) {
        try (final var client = pool.getResource()) {
            client.persist(key);
        }
    }

    /**
     * Создать новый ключ без TTL или дефолтным(в зависимости от настройки сервера редиса)
     * @param key
     * @param value
     */
    public static synchronized void set(final JedisPool pool, final String key, final String value) {
        try (final var client = pool.getResource()) {
            client.set(key, value);
        }
    }

    /**
     * Создать новый ключ с TTL(в секундах)
     * @param key
     * @param value
     * @param ttl
     */
    public static synchronized void set(final JedisPool pool, final String key, final String value, final int ttl) {
        try (final var client = pool.getResource()) {
            client.setex(key, ttl, value);
        }
    }

    /**
     * Удалить значение по имени ключа
     * @param key
     */
    public static synchronized void del(final JedisPool pool, final String key) {
        try (final var client = pool.getResource()) {
             client.del(key);
        }
    }

    public static synchronized void del(final JedisPool pool, final List<String> data) {
        try (final var client = pool.getResource()) {
            final var p = client.pipelined();
            data.forEach(p::del);
            p.sync();
        }
    }
}
