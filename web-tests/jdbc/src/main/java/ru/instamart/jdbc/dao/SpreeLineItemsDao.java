package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.SpreeLineItemsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SpreeLineItemsDao extends AbstractDao<Long, SpreeLineItemsEntity> {

    public static final SpreeLineItemsDao INSTANCE = new SpreeLineItemsDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_line_items";

    @Override
    public Optional<SpreeLineItemsEntity> findById(Long id) {
        SpreeLineItemsEntity spreeLineItemsEntity = null;
        var sql = String.format(SELECT_SQL, "*") + " WHERE id = ?";
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                spreeLineItemsEntity = new SpreeLineItemsEntity();
                spreeLineItemsEntity.setId(resultSet.getLong("id"));
                spreeLineItemsEntity.setOrderId(resultSet.getLong("order_id"));
                spreeLineItemsEntity.setShipmentId(resultSet.getLong("shipment_id"));
                spreeLineItemsEntity.setOfferId(resultSet.getLong("offer_id"));
                spreeLineItemsEntity.setQuantity(resultSet.getInt("quantity"));
                spreeLineItemsEntity.setPrice(resultSet.getDouble("price"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(spreeLineItemsEntity);
    }
}
