package ru.instamart.jdbc.dao.surgelevel;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.surgelevel.RetailerEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class RetailerDao implements Dao<Long, RetailerEntity> {

    public static final RetailerDao INSTANCE = new RetailerDao();

    @Override
    public boolean delete(Long retailerId) {
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_SURGE_LEVEL).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(" DELETE FROM retailer WHERE id = ? ")) {
            preparedStatement.setLong(1, retailerId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLSurgelevelManager. Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public RetailerEntity save(RetailerEntity ticket) {
        return null;
    }

    @Override
    public void update(RetailerEntity ticket) {

    }

    @Override
    public Optional<RetailerEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<RetailerEntity> findAll() {
        return null;
    }
}
