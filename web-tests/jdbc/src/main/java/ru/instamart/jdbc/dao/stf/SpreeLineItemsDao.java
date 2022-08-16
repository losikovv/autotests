package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeLineItemsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SpreeLineItemsDao extends AbstractDao<Long, SpreeLineItemsEntity> {

    public static final SpreeLineItemsDao INSTANCE = new SpreeLineItemsDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_line_items";

    @Override
    public Optional<SpreeLineItemsEntity> findById(Long id) {
        SpreeLineItemsEntity spreeLineItemsEntity = null;
        final var sql = String.format(SELECT_SQL, "*") + " WHERE id = ?";
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    spreeLineItemsEntity = new SpreeLineItemsEntity();
                    spreeLineItemsEntity.setId(resultSet.getLong("id"));
                    spreeLineItemsEntity.setOrderId(resultSet.getLong("order_id"));
                    spreeLineItemsEntity.setShipmentId(resultSet.getLong("shipment_id"));
                    spreeLineItemsEntity.setOfferId(resultSet.getLong("offer_id"));
                    spreeLineItemsEntity.setQuantity(resultSet.getInt("quantity"));
                    spreeLineItemsEntity.setPrice(resultSet.getDouble("price"));
                    spreeLineItemsEntity.setAssemblyIssue(resultSet.getString("assembly_issue"));
                    spreeLineItemsEntity.setRetailerShelfPrice(resultSet.getDouble("retailer_shelf_price"));
                    spreeLineItemsEntity.setAssembled(resultSet.getInt("assembled"));
                    spreeLineItemsEntity.setQuantity(resultSet.getInt("quantity"));
                    spreeLineItemsEntity.setFoundQuantity(resultSet.getDouble("found_quantity"));
                    spreeLineItemsEntity.setDeletedAt(resultSet.getString("deleted_at"));
                    spreeLineItemsEntity.setUuid(resultSet.getString("uuid"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(spreeLineItemsEntity);
    }

    public SpreeLineItemsEntity getLineItemByOfferIdAndShipmentId(Long offerId, Long shipmentId) {
        SpreeLineItemsEntity  spreeLineItemsEntity = null;
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE offer_id = ? AND shipment_id = ?")) {
            preparedStatement.setLong(1, offerId);
            preparedStatement.setLong(2, shipmentId);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    spreeLineItemsEntity = new SpreeLineItemsEntity();
                    spreeLineItemsEntity.setId(resultSet.getLong("id"));
                    spreeLineItemsEntity.setOrderId(resultSet.getLong("order_id"));
                    spreeLineItemsEntity.setShipmentId(resultSet.getLong("shipment_id"));
                    spreeLineItemsEntity.setOfferId(resultSet.getLong("offer_id"));
                    spreeLineItemsEntity.setQuantity(resultSet.getInt("quantity"));
                    spreeLineItemsEntity.setPrice(resultSet.getDouble("price"));
                    spreeLineItemsEntity.setAssemblyIssue(resultSet.getString("assembly_issue"));
                    spreeLineItemsEntity.setRetailerShelfPrice(resultSet.getDouble("retailer_shelf_price"));
                    spreeLineItemsEntity.setAssembled(resultSet.getInt("assembled"));
                    spreeLineItemsEntity.setQuantity(resultSet.getInt("quantity"));
                    spreeLineItemsEntity.setFoundQuantity(resultSet.getDouble("found_quantity"));
                    spreeLineItemsEntity.setDeletedAt(resultSet.getString("deleted_at"));
                    spreeLineItemsEntity.setUuid(resultSet.getString("uuid"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return  spreeLineItemsEntity;
    }
}
