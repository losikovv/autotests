package ru.instamart.jdbc.dao.shopper;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.shopper.AssembliesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class AssembliesDao extends AbstractDao<Long, AssembliesEntity> {
    public static final AssembliesDao INSTANCE = new AssembliesDao();
    private final String SELECT_SQL = "SELECT * FROM public.assemblies ";

    public AssembliesEntity getByShipmentId(final int id) {
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_SHP).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(SELECT_SQL + "WHERE shipment_id = ?")) {
            preparedStatement.setInt(1, id);
            try(final var resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    final var assemblies = new AssembliesEntity();
                    assemblies.setId(resultSet.getInt("id"));
                    assemblies.setShipmentId(resultSet.getInt("shipment_id"));
                    assemblies.setState(resultSet.getString("state"));
                    assemblies.setCreatedAt(resultSet.getString("created_at"));
                    assemblies.setUpdatedAt(resultSet.getString("updated_at"));
                    assemblies.setInvoiceNumber(resultSet.getString("invoice_number"));
                    assemblies.setInvoiceTotal(resultSet.getBigDecimal("invoice_total"));
                    assemblies.setReceiptsTotal(resultSet.getBigDecimal("receipts_total"));
                    assemblies.setActiveItemsCount(resultSet.getInt("active_items_count"));
                    assemblies.setItemsTotal(resultSet.getBigDecimal("items_total"));
                    assemblies.setDriverId(resultSet.getInt("driver_id"));
                    assemblies.setShippedAt(resultSet.getString("shipped_at"));
                    assemblies.setOriginalShippingTotals(resultSet.getString("original_shipping_totals"));
                    assemblies.setLogisticVolume(resultSet.getBigDecimal("logistic_volume"));
                    assemblies.setTrolleyNumbers(resultSet.getString("trolley_numbers"));
                    assemblies.setPackerId(resultSet.getInt("packer_id"));
                    assemblies.setMarketingSampleItems(resultSet.getString("marketing_sample_items"));
                    assemblies.setCollectingStartedAt(resultSet.getString("collecting_started_at"));
                    assemblies.setPurchasedAt(resultSet.getString("purchased_at"));
                    assemblies.setTimeForCollecting(resultSet.getInt("time_for_collecting"));
                    assemblies.setDriverType(resultSet.getString("driver_type"));
                    return assemblies;
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLManager. Error: " + e.getMessage());
        }
        return null;
    }
}
