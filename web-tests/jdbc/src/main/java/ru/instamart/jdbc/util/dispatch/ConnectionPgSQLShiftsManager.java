package ru.instamart.jdbc.util.dispatch;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.k8s.K8sPortForward;
import ru.sbermarket.common.Crypt;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.testng.Assert.fail;
import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_PGSQL_POOL_SIZE;
import static ru.instamart.kraken.enums.OnDemandServices.SHIFTS;

@Slf4j
public class ConnectionPgSQLShiftsManager {
    private static BlockingQueue<Connection> pool;
    private static List<Connection> sourceConnections;

    static {
        portForward();
        loadDriver();
        initConnectionPool();
    }

    private ConnectionPgSQLShiftsManager() {
    }

    protected static void portForward() {
        K8sPortForward.getInstance().portForwardPgSQLService(SHIFTS.getNamespace(), SHIFTS.getPort());
    }

    public static Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void initConnectionPool() {
        pool = new ArrayBlockingQueue<>(DEFAULT_PGSQL_POOL_SIZE);
        sourceConnections = new ArrayList<>(DEFAULT_PGSQL_POOL_SIZE);
        for (int i = 0; i < DEFAULT_PGSQL_POOL_SIZE; i++) {
            var connection = open();
            var proxyConnection = (Connection)
                    Proxy.newProxyInstance(ConnectionPgSQLShiftsManager.class.getClassLoader(), new Class[]{Connection.class},
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
                    String.format("jdbc:postgresql://localhost:%s/app", SHIFTS.getPort()),
                    Crypt.INSTANCE.decrypt("O4On6ImtTAIvvUDOsqOHDw=="),
                    Crypt.INSTANCE.decrypt("DQdUNB8CjrqEUiIrAaZlCg==")
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
