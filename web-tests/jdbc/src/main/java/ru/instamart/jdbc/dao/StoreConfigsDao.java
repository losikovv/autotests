package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.StoreConfigsEntity;
import ru.instamart.jdbc.entity.StoresEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StoreConfigsDao implements Dao<Long, StoreConfigsEntity> {

    public static final StoreConfigsDao INSTANCE = new StoreConfigsDao();
    private final String SELECT_SQL = "SELECT %s FROM store_configs";
    private final String DELETE_SQL = "DELETE FROM store_configs ";

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public StoreConfigsEntity save(StoreConfigsEntity ticket) {
        return null;
    }

    @Override
    public void update(StoreConfigsEntity ticket) {

    }

    @Override
    public Optional<StoreConfigsEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<StoreConfigsEntity> findAll() {
        return null;
    }

    public void updateEditingSettings(Integer storeId, Integer hours, Integer editingAllowed) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement("UPDATE store_configs SET disallow_order_editing_hours = ?, hours_order_edit_locked = ? WHERE store_id = ?")) {
            preparedStatement.setInt(1, hours);
            preparedStatement.setInt(2, editingAllowed);
            preparedStatement.setLong(3, storeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public StoreConfigsEntity getConfigsByStoreId(Long storeId) {
        StoreConfigsEntity storeConfigs = new StoreConfigsEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE store_id = ?")) {
            preparedStatement.setLong(1, storeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                storeConfigs.setShipmentBaseWeight(resultSet.getDouble("shipment_base_weight"));
                storeConfigs.setMinOrderAmount(resultSet.getInt("min_order_amount"));
                storeConfigs.setMinFirstOrderAmount(resultSet.getInt("min_first_order_amount"));
                storeConfigs.setShipmentBaseItemsCount(resultSet.getInt("shipment_base_items_count"));
                storeConfigs.setMinOrderAmountPickup(resultSet.getInt("min_order_amount_pickup"));
                storeConfigs.setMinFirstOrderAmountPickup(resultSet.getInt("min_first_order_amount_pickup"));
                storeConfigs.setDisallowOrderEditingHours(resultSet.getInt("disallow_order_editing_hours"));
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storeConfigs;
    }

    public void deleteByStoreId(Long storeId) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + "WHERE store_id = ?")) {
            preparedStatement.setLong(1, storeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
