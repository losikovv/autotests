package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.SpreePaymentMethodsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.fail;

public class SpreePaymentMethodsDao extends AbstractDao<Long, SpreePaymentMethodsEntity> {

    public static final SpreePaymentMethodsDao INSTANCE = new SpreePaymentMethodsDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_payment_methods";

    public List<Long> getPaymentMethodsIds() {
        List<Long> ids = new ArrayList<>();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "id") + " WHERE active = 1 AND deleted_at IS NULL")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ids.add(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return ids;
    }
}
