package ru.instamart.kraken.database.util;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.util.Crypt;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.testng.Assert.fail;

@Slf4j
public class ConnectionMySQLManager {

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

    private ConnectionMySQLManager() {
    }

    protected static void initConnectionPool() {
        pool = new ArrayBlockingQueue<>(DEFAULT_POOL_SIZE);
        sourceConnections = new ArrayList<>(DEFAULT_POOL_SIZE);
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            var connection = open();
            var proxyConnection = (Connection)
                    Proxy.newProxyInstance(ConnectionMySQLManager.class.getClassLoader(), new Class[]{Connection.class},
                            (proxy, method, args) -> method.getName().equals("close")
                                    ? pool.add((Connection) proxy)
                                    : method.invoke(connection, args));
            pool.add(proxyConnection);
            sourceConnections.add(connection);
        }
    }

    protected static void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("Error mySQL Driver init");
            fail("Error mySQL Driver init");
        }
    }

    protected static Connection open() {
        try {
            return DriverManager.getConnection
                    (PropertiesUtil.get(URL_KEY) + "?" +
                            "user=" + Crypt.INSTANCE.decrypt(PropertiesUtil.get(USERNAME_KEY)) +
                            "&password=" + Crypt.INSTANCE.decrypt(PropertiesUtil.get(PASSWORD_KEY)));
        } catch (SQLException ex) {
            log.error("SQLException: " + ex.getMessage() +
                    "SQLState: " + ex.getSQLState() +
                    "VendorError: " + ex.getErrorCode());
            fail("SQLException: " + ex.getMessage());
        }
        return null;
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
