package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.SpreePaymentMethodsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpreePaymentMethodsDao implements Dao<Long, SpreePaymentMethodsEntity> {

    public static final SpreePaymentMethodsDao INSTANCE = new SpreePaymentMethodsDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_payment_methods";


    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public SpreePaymentMethodsEntity save(SpreePaymentMethodsEntity ticket) {
        return null;
    }

    @Override
    public void update(SpreePaymentMethodsEntity ticket) {

    }

    @Override
    public Optional<SpreePaymentMethodsEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<SpreePaymentMethodsEntity> findAll() {
        return null;
    }

    public List<Long> getPaymentMethodsIds() {
        List<Long> ids = new ArrayList<>();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "id") + " WHERE active = 1 AND deleted_at IS NULL")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
             ids.add(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }
}
