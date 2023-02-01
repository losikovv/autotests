package ru.instamart.jdbc.dao.customer_payments;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.customer_payments.InvoiceEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;

import static org.testng.Assert.fail;

public class InvoicesDao extends AbstractDao<Long, InvoiceEntity> {
    public static final InvoicesDao INSTANCE = new InvoicesDao();

    public InvoiceEntity getInvoiceByShipmentUuid(final String shipmentUuid) {
        InvoiceEntity invoiceEntity = null;
        try (final var connect = ConnectionManager.getDataSource(Db.PG_PAYMENTS).getConnection();
             final var preparedStatement = connect.prepareStatement("SELECT * FROM invoices WHERE shipment_uuid = ?::uuid")) {
            preparedStatement.setString(1, shipmentUuid);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    invoiceEntity = new InvoiceEntity();
                    invoiceEntity.setId(resultSet.getInt("id"));
                    invoiceEntity.setShipmentUuid(resultSet.getString("shipment_uuid"));
                    invoiceEntity.setCustomerUuid(resultSet.getString("customer_uuid"));
                    invoiceEntity.setTotalAmount(resultSet.getInt("total_amount"));
                    invoiceEntity.setTotalHoldAmount(resultSet.getInt("total_hold_amount"));
                    invoiceEntity.setTotalRefundAmount(resultSet.getInt("total_refund_amount"));
                    invoiceEntity.setState(resultSet.getString("state"));
                    invoiceEntity.setCreatedAt(resultSet.getTimestamp("created_at"));
                    invoiceEntity.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQL. Error: " + e.getMessage());
        }
        return invoiceEntity;
    }
}
