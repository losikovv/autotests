package ru.instamart.jdbc.util;

import java.sql.Connection;
import java.sql.SQLException;

public final class Transactional implements AutoCloseable {

    private final Connection connection;
    private final boolean initialStateForCommit;
    private boolean isCommitted;

    public Transactional(final Connection connection, final boolean autoCommit) throws SQLException {
        this.connection = connection;
        this.initialStateForCommit = this.connection.getAutoCommit();
        this.connection.setAutoCommit(autoCommit);
    }

    @Override
    public void close() throws SQLException {
        if (!isCommitted) {
            connection.rollback();
        }
        connection.setAutoCommit(initialStateForCommit);
    }

    public void commit() throws SQLException {
        connection.commit();
        isCommitted = true;
    }
}
