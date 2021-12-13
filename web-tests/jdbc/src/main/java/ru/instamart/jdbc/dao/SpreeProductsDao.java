package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.SpreeProductsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreeProductsDao extends AbstractDao<Long, SpreeProductsEntity> {

    public static final SpreeProductsDao INSTANCE = new SpreeProductsDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_products";

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
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
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
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return id;
    }
}
