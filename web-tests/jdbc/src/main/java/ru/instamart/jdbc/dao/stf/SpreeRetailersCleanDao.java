package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.StoresEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreeRetailersCleanDao extends AbstractDao<Long, StoresEntity> {

    public static final SpreeRetailersCleanDao INSTANCE = new SpreeRetailersCleanDao();
    private final String SELECT_FROM_STORES_SQL = "SELECT %s FROM stores";

    private final String DELETE_FROM_STORE_SCHEDULES = "DELETE FROM store_schedules";
    private final String DELETE_FROM_PRICES = "DELETE FROM prices";
    private final String DELETE_FROM_OFFERS = "DELETE FROM offers";
    private final String DELETE_FROM_PAYMENT_METHODS_STORES = "DELETE FROM payment_methods_stores";
    private final String DELETE_FROM_STORE_ZONES = "DELETE FROM store_zones";
    private final String DELETE_FROM_STORES_TENANTS = "DELETE FROM stores_tenants";
    private final String DELETE_FROM_STORE_CONFIGS = "DELETE FROM store_configs";
    private final String DELETE_FROM_STORES = "DELETE FROM stores";
    private final String DELETE_FROM_RETAILERS_SQL = "DELETE FROM spree_retailers";
    private final Connection connect = ConnectionMySQLManager.get();
    private PreparedStatement preparedStatement;

    public void deleteRetailerByName(String retailerName) {
        try {
            preparedStatement = connect.prepareStatement(String.format(SELECT_FROM_STORES_SQL, "id") + " WHERE retailer_id = (SELECT sr.id FROM spree_retailers sr WHERE sr.name = ?)");
            preparedStatement.setString(1, retailerName);
            ResultSet resultSet = preparedStatement.executeQuery();
            connect.setAutoCommit(false);
            while (resultSet.next()) {
                deleteStoreSchedules(resultSet.getInt("id"));
                deletePrices(resultSet.getInt("id"));
                deleteOffers(resultSet.getInt("id"));
                deletePaymentMethodsStores(resultSet.getInt("id"));
                deleteStoreZones(resultSet.getInt("id"));
                deleteStoresTenants(resultSet.getInt("id"));
                deleteStoreConfigs(resultSet.getInt("id"));
                deleteStore(resultSet.getInt("id"));
            }
            deleteRetailer(retailerName);
            connect.commit();
        } catch (SQLException e) {
            try {
                connect.rollback();
            } catch (SQLException ex) {
                fail("Error during rollback. Error: " + ex.getMessage());
            }
            fail("Error ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }

    private void deleteStoreSchedules(int storeId) throws SQLException {
        preparedStatement = connect.prepareStatement(DELETE_FROM_STORE_SCHEDULES + " WHERE store_id = ?");
        preparedStatement.setInt(1, storeId);
        preparedStatement.executeUpdate();
    }

    private void deletePrices(int storeId) throws SQLException {
        preparedStatement = connect.prepareStatement(DELETE_FROM_PRICES + " WHERE store_id = ?");
        preparedStatement.setInt(1, storeId);
        preparedStatement.executeUpdate();
    }

    private void deleteOffers(int storeId) throws SQLException {
        preparedStatement = connect.prepareStatement(DELETE_FROM_OFFERS + " WHERE store_id = ?");
        preparedStatement.setInt(1, storeId);
        preparedStatement.executeUpdate();
    }

    private void deletePaymentMethodsStores(int storeId) throws SQLException {
        preparedStatement = connect.prepareStatement(DELETE_FROM_PAYMENT_METHODS_STORES + " WHERE store_id = ?");
        preparedStatement.setInt(1, storeId);
        preparedStatement.executeUpdate();
    }

    private void deleteStoreZones(int storeId) throws SQLException {
        preparedStatement = connect.prepareStatement(DELETE_FROM_STORE_ZONES + " WHERE store_id = ?");
        preparedStatement.setInt(1, storeId);
        preparedStatement.executeUpdate();
    }

    private void deleteStoresTenants(int storeId) throws SQLException {
        preparedStatement = connect.prepareStatement(DELETE_FROM_STORES_TENANTS + " WHERE store_id = ?");
        preparedStatement.setInt(1, storeId);
        preparedStatement.executeUpdate();
    }

    private void deleteStoreConfigs(int storeId) throws SQLException {
        preparedStatement = connect.prepareStatement(DELETE_FROM_STORE_CONFIGS + " WHERE store_id = ?");
        preparedStatement.setInt(1, storeId);
        preparedStatement.executeUpdate();
    }

    private void deleteStore(int storeId) throws SQLException {
        preparedStatement = connect.prepareStatement(DELETE_FROM_STORES + " WHERE id = ?");
        preparedStatement.setInt(1, storeId);
        preparedStatement.executeUpdate();
    }

    private void deleteRetailer(String retailerName) throws SQLException {
        preparedStatement = connect.prepareStatement(DELETE_FROM_RETAILERS_SQL + " WHERE name = ?");
        preparedStatement.setString(1, retailerName);
        preparedStatement.executeUpdate();
    }
}
