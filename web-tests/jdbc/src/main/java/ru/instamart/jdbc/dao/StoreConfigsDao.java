package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.StoreConfigsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StoreConfigsDao implements Dao<Long, StoreConfigsEntity> {

    public static final StoreConfigsDao INSTANCE = new StoreConfigsDao();

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
}
