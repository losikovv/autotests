package ru.instamart.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Set;

public final class RedisService {
    
    /**
     * Переименование value по ключу
     * @param key
     * @param newValue
     */
    public static void update(final JedisPool pool, final String key, final String newValue) {
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
    public static void rename(final JedisPool pool, final String fromKey, final String toKey) {
        try (final var client = pool.getResource()) {
            client.rename(fromKey, toKey);
        }
    }

    /**
     *
     * @param key
     * @return - проверка на существование ключа
     */
    public static boolean isExist(final JedisPool pool, final String key) {
        try (final var client = pool.getResource()) {
            return client.exists(key);
        }
    }

    /**
     *
     * @param key
     * @return - тип хранимого значения
     */
    public static String getType(final JedisPool pool, final String key) {
        try (final var client = pool.getResource()) {
            return client.type(key);
        }
    }

    /**
     *
     * @return - получить список всех ключей
     */
    public static Set<String> getAll(final JedisPool pool) {
        try (final var client = pool.getResource()) {
            return client.keys("*");
        }
    }

    /**
     *
     * @param key
     * @return - получить value по ключу
     */
    public static String get(final JedisPool pool, final String key) {
        try (final var client = pool.getResource()) {
            return client.get(key);
        }
    }

    /**
     * Отправка батчем(одним большим упакованным запросом, вместо кучи мелких)
     * @param data
     */
    public static void set(final JedisPool pool, final Map<String, String> data) {
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
    public static long getTtl(final JedisPool pool, final String key) {
        try (final var client = pool.getResource()) {
            return client.ttl(key);
        }
    }

    /**
     * Установить TTL для ключа
     * @param key
     * @param ttl - время в секундах
     */
    public static void setTtl(final JedisPool pool, final String key, final int ttl) {
        try (final var client = pool.getResource()) {
            client.expire(key, ttl);
        }
    }

    /**
     * Удалить TTL
     * @param key - имя
     */
    public static void removeTtl(final JedisPool pool, final String key) {
        try (final var client = pool.getResource()) {
            client.persist(key);
        }
    }

    /**
     * Создать новый ключ без TTL или дефолтным(в зависимости от настройки сервера редиса)
     * @param key
     * @param value
     */
    public static void set(final JedisPool pool, final String key, final String value) {
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
    public static void set(final JedisPool pool, final String key, final String value, final int ttl) {
        try (final var client = pool.getResource()) {
            client.setex(key, ttl, value);
        }
    }

    /**
     * Удалить значение по имени ключа
     * @param key
     */
    public static void del(final JedisPool pool, final String key) {
        try (final var client = pool.getResource()) {
            client.del(key);
        }
    }
}
