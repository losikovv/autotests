package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.StoreConfigsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class StoreConfigsDao extends AbstractDao<Long, StoreConfigsEntity> {

    public static final StoreConfigsDao INSTANCE = new StoreConfigsDao();
    private final String SELECT_SQL = "SELECT %s FROM store_configs";
    private final String DELETE_SQL = "DELETE FROM store_configs ";

    public void updateEditingSettings(Integer storeId, Integer hours, Integer editingAllowed) {
        try (Connection connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement("UPDATE store_configs SET disallow_order_editing_hours = ?, hours_order_edit_locked = ? WHERE store_id = ?")) {
            preparedStatement.setInt(1, hours);
            preparedStatement.setInt(2, editingAllowed);
            preparedStatement.setLong(3, storeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }

    public boolean updateOrdersApiIntegrationType(final Long storeId, final Integer ordersApiIntegrationType) {
        try (Connection connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement("UPDATE store_configs SET orders_api_integration_type = ? WHERE store_id = ?")) {
            preparedStatement.setInt(1, ordersApiIntegrationType);
            preparedStatement.setLong(2, storeId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return false;
    }

    public StoreConfigsEntity getConfigsByStoreId(Integer storeId) {
        final var storeConfigs = new StoreConfigsEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE store_id = ?")) {
            preparedStatement.setLong(1, storeId);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    storeConfigs.setShipmentBaseWeight(resultSet.getDouble("shipment_base_weight"));
                    storeConfigs.setMinOrderAmount(resultSet.getInt("min_order_amount"));
                    storeConfigs.setMinFirstOrderAmount(resultSet.getInt("min_first_order_amount"));
                    storeConfigs.setShipmentBaseItemsCount(resultSet.getInt("shipment_base_items_count"));
                    storeConfigs.setMinOrderAmountPickup(resultSet.getInt("min_order_amount_pickup"));
                    storeConfigs.setMinFirstOrderAmountPickup(resultSet.getInt("min_first_order_amount_pickup"));
                    storeConfigs.setDisallowOrderEditingHours(resultSet.getInt("disallow_order_editing_hours"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return storeConfigs;
    }

    public void deleteByStoreId(Integer storeId) {
        try (Connection connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + "WHERE store_id = ?")) {
            preparedStatement.setLong(1, storeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
