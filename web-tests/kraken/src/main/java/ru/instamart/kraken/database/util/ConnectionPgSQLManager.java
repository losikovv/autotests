package ru.instamart.kraken.database.util;

import ru.instamart.kraken.util.Crypt;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPgSQLManager {

    private static final String PASSWORD_KEY = "db.password";
    private static final String USERNAME_KEY = "db.username";
    private static final String URL_KEY = "db.url";
    private static final Integer DEFAULT_POOL_SIZE = 5;
    private static BlockingQueue<Connection> pool;
    private static List<Connection> sourceConnections;

    static {
        loadDriver();
        initConnectionPool();
    }

    private ConnectionPgSQLManager() {
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
                    PropertiesUtil.get(URL_KEY),
                    Crypt.INSTANCE.decrypt(PropertiesUtil.get(USERNAME_KEY)),
                    Crypt.INSTANCE.decrypt(PropertiesUtil.get(PASSWORD_KEY))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closePool() {
        try {
            for (Connection sourceConnection : sourceConnections) {
                sourceConnection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
