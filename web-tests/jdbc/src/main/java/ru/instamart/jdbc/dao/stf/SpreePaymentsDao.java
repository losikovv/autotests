package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreePaymentsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreePaymentsDao extends AbstractDao<Long, SpreePaymentsEntity> {

    public static final SpreePaymentsDao INSTANCE = new SpreePaymentsDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_payments";

    public SpreePaymentsEntity getPaymentByOrderId(Long orderId) {
        SpreePaymentsEntity spreePaymentsEntity = null;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE order_id = ? ORDER BY id DESC LIMIT 1")) {
            preparedStatement.setLong(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                spreePaymentsEntity = new SpreePaymentsEntity();
                spreePaymentsEntity.setId((resultSet.getLong("id")));
                spreePaymentsEntity.setAmount((resultSet.getDouble("amount")));
                spreePaymentsEntity.setPaymentMethodId((resultSet.getInt("payment_method_id")));
                spreePaymentsEntity.setState((resultSet.getString("state")));
                spreePaymentsEntity.setIdentifier((resultSet.getString("identifier")));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return spreePaymentsEntity;
    }

    public int getCountByOrderId(Long orderId) {
        int resultCount = 0;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total") + " WHERE order_id = ?")) {
            preparedStatement.setLong(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            resultCount = resultSet.getInt("total");
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return resultCount;
    }
}
