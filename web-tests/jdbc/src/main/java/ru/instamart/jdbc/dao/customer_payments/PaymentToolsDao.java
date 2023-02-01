package ru.instamart.jdbc.dao.customer_payments;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.customer_payments.PaymentToolEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;

import static org.testng.Assert.fail;

public class PaymentToolsDao extends AbstractDao<Long, PaymentToolEntity> {
    public static final PaymentToolsDao INSTANCE = new PaymentToolsDao();

    public PaymentToolEntity getActivePaymentTool() {
        PaymentToolEntity paymentToolEntity = null;
        try (final var connect = ConnectionManager.getDataSource(Db.PG_PAYMENTS).getConnection();
             final var preparedStatement = connect.prepareStatement("SELECT * FROM payment_tools pt WHERE state = 'authorized' ORDER BY updated_at DESC")) {
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    paymentToolEntity = new PaymentToolEntity();
                    paymentToolEntity.setId(resultSet.getInt("id"));
                    paymentToolEntity.setUuid(resultSet.getString("uuid"));
                    paymentToolEntity.setPaymentMethodId(resultSet.getInt("payment_method_id"));
                    paymentToolEntity.setCustomerUuid(resultSet.getString("customer_uuid"));
                    paymentToolEntity.setTitle(resultSet.getString("title"));
                    paymentToolEntity.setPaymentToken(resultSet.getString("payment_token"));
                    paymentToolEntity.setCreatedAt(resultSet.getTimestamp("created_at"));
                    paymentToolEntity.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    paymentToolEntity.setMerchantId(resultSet.getString("merchant_id"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQL. Error: " + e.getMessage());
        }
        return paymentToolEntity;
    }
}
