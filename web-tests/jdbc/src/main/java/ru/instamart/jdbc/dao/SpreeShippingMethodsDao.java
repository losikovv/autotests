package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.SpreeShippingMethods;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SpreeShippingMethodsDao implements Dao<Long, SpreeShippingMethods>{

    public static final SpreeShippingMethodsDao INSTANCE = new SpreeShippingMethodsDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_shipping_methods";

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public SpreeShippingMethods save(SpreeShippingMethods ticket) {
        return null;
    }

    @Override
    public void update(SpreeShippingMethods ticket) {

    }

    @Override
    public Optional<SpreeShippingMethods> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<SpreeShippingMethods> findAll() {
        return null;
    }

    public Long getShippingMethodId(String kind) {
        Long id = null;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "id") + " WHERE kind = ? AND deleted_at IS NULL LIMIT 1")) {
            preparedStatement.setString(1, kind);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getLong("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
