package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.PaymentMethodStoresEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class PaymentMethodStoresDao extends AbstractDao<Long, PaymentMethodStoresEntity> {

    public static final PaymentMethodStoresDao INSTANCE = new PaymentMethodStoresDao();
    private final String DELETE_SQL = "DELETE FROM payment_methods_stores";

    public void deletePaymentMethodByStoreId(int storeId) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE store_id = ?")) {
            preparedStatement.setInt(1, storeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
