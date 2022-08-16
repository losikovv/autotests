package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.FiscalReceiptsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;

import static org.testng.Assert.fail;

public class FiscalReceiptsDao extends AbstractDao<Long, FiscalReceiptsEntity> {

    public static final FiscalReceiptsDao INSTANCE = new FiscalReceiptsDao();
    private static final String SELECT_SQL = "SELECT %s FROM fiscal_receipts";

    public FiscalReceiptsEntity getReceiptByShipmenId(Long shipmentId) {
        final var receipt = new FiscalReceiptsEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE shipment_id = ?")) {
            preparedStatement.setLong(1, shipmentId);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    receipt.setId(resultSet.getLong("id"));
                    receipt.setTotal(resultSet.getDouble("total"));
                    receipt.setTransactionDetails(resultSet.getString("transaction_details"));
                    receipt.setFiscalDocumentNumber(resultSet.getString("fiscal_document_number"));
                    receipt.setFiscalChecksum(resultSet.getString("fiscal_checksum"));
                    receipt.setFiscalSecret(resultSet.getString("fiscal_secret"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return receipt;
    }
}
