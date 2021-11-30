package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.SpreeOrdersEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SpreeOrdersDao implements Dao<Long, SpreeOrdersEntity>{

    public static final SpreeOrdersDao INSTANCE = new SpreeOrdersDao();

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public SpreeOrdersEntity save(SpreeOrdersEntity ticket) {
        return null;
    }

    @Override
    public void update(SpreeOrdersEntity ticket) {

    }

    @Override
    public Optional<SpreeOrdersEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<SpreeOrdersEntity> findAll() {
        return null;
    }

    public void updateShippingKind(String orderNumber, String shippingKind) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement("UPDATE spree_orders SET shipping_method_kind = ? WHERE number = ?")) {
            preparedStatement.setString(1, shippingKind);
            preparedStatement.setString(2, orderNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
