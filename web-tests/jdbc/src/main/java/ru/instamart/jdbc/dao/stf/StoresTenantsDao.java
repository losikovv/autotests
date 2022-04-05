package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.StoresTenantsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class StoresTenantsDao extends AbstractDao<Long, StoresTenantsEntity> {

    public static final StoresTenantsDao INSTANCE = new StoresTenantsDao();
    private final String DELETE_SQL = "DELETE FROM stores_tenants";
    private final String INSERT_SQL = "INSERT INTO stores_tenants";

    public void addStoreTenant(Integer storeId, String tenant) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(INSERT_SQL + " (tenant_id, store_id) VALUES(?, ?)")) {
            preparedStatement.setString(1, tenant);
            preparedStatement.setInt(2, storeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }

    public void deleteStoreTenantByStoreId(Integer storeId) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE store_id = ?")) {
            preparedStatement.setLong(1, storeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
