package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.DeliveryWindowsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DeliveryWindowsDao implements Dao<Long, DeliveryWindowsEntity> {

    public static final DeliveryWindowsDao INSTANCE = new DeliveryWindowsDao();
    private final String SELECT_SQL = "SELECT %s FROM delivery_windows";


    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public DeliveryWindowsEntity save(DeliveryWindowsEntity ticket) {
        return null;
    }

    @Override
    public void update(DeliveryWindowsEntity ticket) {

    }

    @Override
    public Optional<DeliveryWindowsEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<DeliveryWindowsEntity> findAll() {
        return null;
    }

    public int getCount(Long storeId, String start, String end) {
        int resultCount = 0;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total") + " WHERE store_id = ? AND active = 1 AND starts_at BETWEEN ? AND ?")) {
            preparedStatement.setLong(1, storeId);
            preparedStatement.setString(2, start);
            preparedStatement.setString(3, end);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            resultCount = resultSet.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultCount;
    }
}
