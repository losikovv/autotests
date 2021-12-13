package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.SpreeOrdersEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreeOrdersDao extends AbstractDao<Long, SpreeOrdersEntity> {

    public static final SpreeOrdersDao INSTANCE = new SpreeOrdersDao();

    public void updateShippingKind(String orderNumber, String shippingKind) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement("UPDATE spree_orders SET shipping_method_kind = ? WHERE number = ?")) {
            preparedStatement.setString(1, shippingKind);
            preparedStatement.setString(2, orderNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
