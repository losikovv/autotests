package ru.instamart.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import ru.instamart.k8s.K8sPortForward;
import ru.instamart.kraken.util.Socket;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class RedisManager {

    private static final Map<String, JedisPool> REDIS_POOL = new ConcurrentHashMap<>();

    public static synchronized JedisPool getConnection(final Redis redis) {
        return REDIS_POOL.computeIfAbsent(redis.name(), source -> createClient(redis));
    }

    private static JedisPool createClient(final Redis redis) {
        redis.setInternalPort(Socket.findAvailablePort());
        K8sPortForward.INSTANCE.portForward(redis.getNamespace(), redis.getLabel(), redis.getInternalPort(), redis.getContainerPort());
        return new JedisPool(buildPoolConfig(), redis.getUrl(), redis.getInternalPort());
    }

    private static JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(30);
        poolConfig.setMaxIdle(10);
        poolConfig.setMinIdle(5);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTime(Duration.ofMillis(60000));
        poolConfig.setTimeBetweenEvictionRuns(Duration.ofMillis(30000));
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }
}
