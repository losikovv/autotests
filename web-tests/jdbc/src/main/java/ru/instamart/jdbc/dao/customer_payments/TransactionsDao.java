package ru.instamart.jdbc.dao.customer_payments;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.customer_payments.TransactionEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;

import static org.testng.Assert.fail;

public class TransactionsDao extends AbstractDao<Long, TransactionEntity> {
    public static final TransactionsDao INSTANCE = new TransactionsDao();

    public TransactionEntity getTransactionByUuid(String uuid) {
        TransactionEntity transactionEntity = null;
        try (final var connect = ConnectionManager.getDataSource(Db.PG_PAYMENTS).getConnection();
             final var preparedStatement =
                     connect.prepareStatement("SELECT * FROM transactions WHERE uuid = ?::uuid")) {
            preparedStatement.setString(1, uuid);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    transactionEntity = new TransactionEntity();
                    transactionEntity.setId(resultSet.getLong("id"));
                    transactionEntity.setUuid(resultSet.getString("uuid"));
                    transactionEntity.setInvoiceId(resultSet.getInt("invoice_id"));
                    transactionEntity.setPaymentToolId(resultSet.getInt("payment_tool_id"));
                    transactionEntity.setAmount(resultSet.getInt("amount"));
                    transactionEntity.setHoldAmount(resultSet.getInt("hold_amount"));
                    transactionEntity.setRefundAmount(resultSet.getInt("refund_amount"));
                    transactionEntity.setState(resultSet.getString("state"));
                    transactionEntity.setCreatedAt(resultSet.getTimestamp("created_at"));
                    transactionEntity.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    transactionEntity.setMerchantId(resultSet.getString("merchant_id"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQL. Error: " + e);
        }
        return transactionEntity;
    }
}
