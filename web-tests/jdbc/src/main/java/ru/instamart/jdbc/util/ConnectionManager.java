package ru.instamart.jdbc.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.k8s.K8sPortForward;
import ru.instamart.kraken.util.Socket;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public final class ConnectionManager {

    private static final Map<String, DataSource> DB_POOL = new ConcurrentHashMap<>();

    public static synchronized DataSource getDataSource(final Db db) throws SQLException {
        return DB_POOL.computeIfAbsent(db.name(), source -> createDataSource(db));
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

        hikariConfig.setPoolName(db.name());
        hikariConfig.setMaximumPoolSize(db.getPoolSize());

        hikariConfig.setConnectionTimeout(Duration.ofSeconds(20).toMillis());
        hikariConfig.setMaxLifetime(Duration.ofMinutes(10).toMillis());
        hikariConfig.setLeakDetectionThreshold(Duration.ofMinutes(2).toMillis());

        if (db.getType() == DbType.MYSQL) {
            // {@link https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration}
            // {@link https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-configuration-properties.html}
            // {@link https://cdn.oreillystatic.com/en/assets/1/event/21/Connector_J%20Performance%20Gems%20Presentation.pdf}
            hikariConfig.addDataSourceProperty("cachePrepStmts", true);
            hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
            hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
            hikariConfig.addDataSourceProperty("useServerPrepStmts", true);
            hikariConfig.addDataSourceProperty("useLocalSessionState", true);
            hikariConfig.addDataSourceProperty("useLocalTransactionState", true);
            hikariConfig.addDataSourceProperty("rewriteBatchedStatements", true);
            hikariConfig.addDataSourceProperty("cacheResultSetMetadata", true);
            hikariConfig.addDataSourceProperty("cacheServerConfiguration", true);
            hikariConfig.addDataSourceProperty("elideSetAutoCommits", true);
            hikariConfig.addDataSourceProperty("maintainTimeStats", false);
        }

        return hikariConfig;
    }

    private static String formatUrl(final Db db) {
        if (db.getUrl().contains("%s")) {
            return String.format(db.getUrl(), db.getInternalPort());
        }
        return db.getUrl();
    }
}
