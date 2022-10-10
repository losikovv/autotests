package ru.instamart.jdbc.dao.surgelevel;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.surgelevel.StoreEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class StoreDao extends AbstractDao<String, StoreEntity> {

    public static final StoreDao INSTANCE = new StoreDao();
    private final String INSERT_SQL = "INSERT INTO store ";
    private final String DELETE_SQL = "DELETE FROM store ";

    public boolean addStore(String storeId, String configId, Boolean ondemand, Float lat, Float lon, Integer deliveryArea){
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_SURGE_LEVEL).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(INSERT_SQL + " (id, config_id, ondemand, lat, lon, deliveryarea) " +
                     " VALUES (?::uuid, ?::uuid, ?, ?, ?, ?) ")) {
            preparedStatement.setString(1, storeId);
            preparedStatement.setString(2, configId);
            preparedStatement.setBoolean(3, ondemand);
            preparedStatement.setFloat(4, lat);
            preparedStatement.setFloat(5, lon);
            preparedStatement.setInt(6, deliveryArea);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLSurgelevelManager. Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(String storeId) {
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_SURGE_LEVEL).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE id = ?::uuid ")) {
            preparedStatement.setString(1, storeId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLSurgelevelManager. Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Optional<StoreEntity> findById(String id) {
        return Optional.empty();
    }
}
