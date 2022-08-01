package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.StoresEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;
import ru.instamart.jdbc.util.Transactional;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.testng.Assert.fail;

public class SpreeRetailersCleanDao extends AbstractDao<Long, StoresEntity> {

    public static final SpreeRetailersCleanDao INSTANCE = new SpreeRetailersCleanDao();
    private final String SELECT_FROM_STORES_SQL = "SELECT stores.* FROM stores JOIN spree_retailers ON stores.retailer_id = spree_retailers.id WHERE spree_retailers.name = '%s'";

    private final String DELETE_FROM_STORE_LOCATIONS_SQL = "DELETE FROM store_locations WHERE store_id = ";
    private final String DELETE_FROM_STORE_SCHEDULES_SQL = "DELETE FROM store_schedules WHERE store_id = ";
    private final String DELETE_FROM_PRICES_SQL = "DELETE FROM prices WHERE store_id = ";
    private final String DELETE_FROM_OFFERS_SQL = "DELETE FROM offers WHERE store_id = ";
    private final String DELETE_FROM_PAYMENT_METHODS_STORES_SQL = "DELETE FROM payment_methods_stores WHERE store_id = ";
    private final String DELETE_FROM_STORES_TENANTS_SQL = "DELETE FROM stores_tenants WHERE store_id = ";
    private final String DELETE_FROM_STORE_ZONES_SQL = "DELETE FROM store_zones WHERE store_id = ";
    private final String DELETE_FROM_STORE_CONFIGS_SQL = "DELETE FROM store_configs WHERE store_id = ";
    private final String DELETE_FROM_STORES_SQL = "DELETE FROM stores WHERE id = ";
    private final String DELETE_FROM_RETAILERS_SQL = "DELETE FROM spree_retailers WHERE name = '%s'";

    public void deleteRetailerWithStores(String retailerName) {
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             Statement statement = connect.createStatement();
             Transactional transactional = new Transactional(connect, false);
        ) {
            ResultSet resultSet = statement.executeQuery(String.format(SELECT_FROM_STORES_SQL, retailerName));
            while (resultSet.next()) {
                statement.addBatch(DELETE_FROM_STORE_LOCATIONS_SQL + resultSet.getInt("id"));
                statement.addBatch(DELETE_FROM_STORE_SCHEDULES_SQL + resultSet.getInt("id"));
                statement.addBatch(DELETE_FROM_PRICES_SQL + resultSet.getInt("id"));
                statement.addBatch(DELETE_FROM_OFFERS_SQL + resultSet.getInt("id"));
                statement.addBatch(DELETE_FROM_PAYMENT_METHODS_STORES_SQL + resultSet.getInt("id"));
                statement.addBatch(DELETE_FROM_STORES_TENANTS_SQL + resultSet.getInt("id"));
                statement.addBatch(DELETE_FROM_STORE_ZONES_SQL + resultSet.getInt("id"));
                statement.addBatch(DELETE_FROM_STORE_CONFIGS_SQL + resultSet.getInt("id"));
                statement.addBatch(DELETE_FROM_STORES_SQL + resultSet.getInt("id"));
            }
            statement.addBatch(String.format(DELETE_FROM_RETAILERS_SQL, retailerName));
            statement.executeBatch();
            transactional.commit();
        } catch (SQLException e) {
            fail("Error ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
