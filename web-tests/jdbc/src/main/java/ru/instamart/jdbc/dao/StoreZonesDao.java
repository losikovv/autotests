package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.StoreZonesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class StoreZonesDao extends AbstractDao<Long, StoreZonesEntity> {

    public static final StoreZonesDao INSTANCE = new StoreZonesDao();
    private final String DELETE_SQL = "DELETE FROM store_zones";

    public void deleteStoreZoneByStoreId(Integer storeId) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE store_id = ?")) {
            preparedStatement.setLong(1, storeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}