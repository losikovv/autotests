package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.FiscalReceiptsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class FiscalReceiptsDao extends AbstractDao<Long, FiscalReceiptsEntity> {

    public static final FiscalReceiptsDao INSTANCE = new FiscalReceiptsDao();
    private static final String SELECT_SQL = "SELECT %s FROM fiscal_receipts";

    public FiscalReceiptsEntity getReceiptByShipmenId(Long shipmentId) {
        FiscalReceiptsEntity receipt = new FiscalReceiptsEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE shipment_id = ?")) {
            preparedStatement.setLong(1, shipmentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                receipt.setId(resultSet.getLong("id"));
                receipt.setTotal(resultSet.getDouble("total"));
                receipt.setTransactionDetails(resultSet.getString("transaction_details"));
                receipt.setFiscalDocumentNumber(resultSet.getString("fiscal_document_number"));
                receipt.setFiscalChecksum(resultSet.getString("fiscal_checksum"));
                receipt.setFiscalSecret(resultSet.getString("fiscal_secret"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return receipt;
    }
}
