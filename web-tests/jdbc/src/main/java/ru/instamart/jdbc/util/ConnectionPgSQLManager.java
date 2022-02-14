package ru.instamart.jdbc.util;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.k8s.K8sPortForward;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.testng.Assert.fail;

@Slf4j
public class ConnectionPgSQLManager {

    private static final String CI_PIPELINE_SOURCE = Optional.ofNullable(System.getenv("CI_PIPELINE_SOURCE")).orElse("local");
    private static final Integer DEFAULT_POOL_SIZE = 5;
    private static BlockingQueue<Connection> pool;
    private static List<Connection> sourceConnections;

    static {
        portForward();
        loadDriver();
        initConnectionPool();
    }

    private ConnectionPgSQLManager() {
    }

    protected static void portForward() {
        //TODO: костыль для локального запуска. Нет доступа по vpn до бд
        if (CI_PIPELINE_SOURCE.equals("local")) {
            K8sPortForward.getInstance().portForwardPgSQL();
        }
    }

    public static Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void initConnectionPool() {
        pool = new ArrayBlockingQueue<>(DEFAULT_POOL_SIZE);
        sourceConnections = new ArrayList<>(DEFAULT_POOL_SIZE);
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            var connection = open();
            var proxyConnection = (Connection)
                    Proxy.newProxyInstance(ConnectionPgSQLManager.class.getClassLoader(), new Class[]{Connection.class},
                            (proxy, method, args) -> method.getName().equals("close")
                                    ? pool.add((Connection) proxy)
                                    : method.invoke(connection, args));
            pool.add(proxyConnection);
            sourceConnections.add(connection);
        }
    }

    protected static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected static Connection open() {
        try {
            return DriverManager.getConnection(
                    //TODO: костыль для локального запуска. Нет доступа по vpn до бд
                    CI_PIPELINE_SOURCE.equals("local") ? "jdbc:postgresql://localhost:6432/shopper_staging_kraken" : EnvironmentProperties.DB_PGSQL_URL,
                    EnvironmentProperties.DB_PGSQL_USERNAME,
                    EnvironmentProperties.DB_PGSQL_PASSWORD
            );
        } catch (SQLException ex) {
            log.error("SQLException: " + ex.getMessage() +
                    "SQLState: " + ex.getSQLState() +
                    "VendorError: " + ex.getErrorCode());
            fail("SQLException: " + ex.getMessage());
        }
        return null;
    }
}
