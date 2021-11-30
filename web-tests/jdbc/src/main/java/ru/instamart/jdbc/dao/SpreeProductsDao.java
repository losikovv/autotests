package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.SpreeProductsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SpreeProductsDao implements Dao<Long, SpreeProductsEntity>{

    public static final SpreeProductsDao INSTANCE = new SpreeProductsDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_products";

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public SpreeProductsEntity save(SpreeProductsEntity ticket) {
        return null;
    }

    @Override
    public void update(SpreeProductsEntity ticket) {

    }

    @Override
    public Optional<SpreeProductsEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<SpreeProductsEntity> findAll() {
        return null;
    }

    public Long getOfferIdBySku(String sku, Integer storeId) {
        Long id = null;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "o.id") +
                    " sp JOIN offers o ON sp.id = o.product_id WHERE sp.sku = ? AND o.store_id = ?")) {
            preparedStatement.setString(1, sku);
            preparedStatement.setInt(2, storeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getLong("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public Long getOfferIdForAlcohol(Integer storeId) {
        Long id = null;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "o.id") +
                     " sp JOIN offers o ON sp.id = o.product_id WHERE sp.shipping_category_id = 3 AND o.store_id = ? AND o.published = 1 AND sp.deleted_at IS NULL AND o.deleted_at IS NULL")) {
            preparedStatement.setInt(1, storeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getLong("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
