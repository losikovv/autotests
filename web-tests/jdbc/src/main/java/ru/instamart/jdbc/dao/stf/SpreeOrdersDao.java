package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeOrdersEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreeOrdersDao extends AbstractDao<Long, SpreeOrdersEntity> {

    public static final SpreeOrdersDao INSTANCE = new SpreeOrdersDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_orders";

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

    public SpreeOrdersEntity getOrderByNumber(String orderNumber) {
        SpreeOrdersEntity order = new SpreeOrdersEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE number = ?")) {
            preparedStatement.setString(1, orderNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order.setId(resultSet.getLong("id"));
                order.setState(resultSet.getString("state"));
                order.setUserId(resultSet.getLong("user_id"));
                order.setShipAddressId(resultSet.getLong("ship_address_id"));
                order.setShipmentState(resultSet.getString("shipment_state"));
                order.setPaymentState(resultSet.getString("payment_state"));
                order.setEmail(resultSet.getString("email"));
                order.setEmail(resultSet.getString("email"));
                order.setReplacementPolicyId(resultSet.getInt("replacement_policy_id"));
                order.setShippingMethodKind(resultSet.getString("shipping_method_kind"));
                order.setPaymentToolId(resultSet.getLong("payment_tool_id"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return order;
    }
}
