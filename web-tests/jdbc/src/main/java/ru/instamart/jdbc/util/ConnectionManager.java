package ru.instamart.jdbc.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.k8s.K8sPortForward;
import ru.instamart.kraken.util.Socket;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public final class ConnectionManager {

    private static final Map<String, DataSource> DB_POOL = new ConcurrentHashMap<>();

    public static synchronized Connection getConnection(final Db db) throws SQLException {
        return DB_POOL.computeIfAbsent(db.name(), source -> createDataSource(db)).getConnection();
    }

    private static DataSource createDataSource(final Db db) {
        final var hikariConfig = getHikariConfig(db);
        log.debug("Create hikari config for {}", db);
        return new HikariDataSource(hikariConfig);
    }

    private static HikariConfig getHikariConfig(final Db db) {
        final var hikariConfig = new HikariConfig();
        db.setInternalPort(Socket.findAvailablePort());
        K8sPortForward.INSTANCE.portForward(db.getNamespace(), db.getLabel(), db.getInternalPort(), db.getContainerPort());
        hikariConfig.setJdbcUrl(formatUrl(db));
        hikariConfig.setUsername(db.getUsername());
        hikariConfig.setPassword(db.getPassword());
        hikariConfig.setDriverClassName(db.getDriver());

        hikariConfig.setPoolName(db.name());
        hikariConfig.setMaximumPoolSize(db.getPoolSize());

        hikariConfig.setConnectionTimeout(Duration.ofSeconds(10).toMillis());
        hikariConfig.setMinimumIdle(10);
        hikariConfig.setMaxLifetime(Duration.ofMinutes(30).toMillis());

        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return hikariConfig;
    }

    private static String formatUrl(final Db db) {
        if (db.getUrl().contains("%s")) {
            return String.format(db.getUrl(), db.getInternalPort());
        }
        return db.getUrl();
    }
}
