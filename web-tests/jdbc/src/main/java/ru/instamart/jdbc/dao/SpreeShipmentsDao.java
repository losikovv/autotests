package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.SpreeShipmentsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreeShipmentsDao extends AbstractDao<Long, SpreeShipmentsEntity> {

    public static final SpreeShipmentsDao INSTANCE = new SpreeShipmentsDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_shipments";

    public SpreeShipmentsEntity getShipmentByNumber(String shipmentNumber) {
        SpreeShipmentsEntity shipment = new SpreeShipmentsEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE number = ?")) {
            preparedStatement.setString(1, shipmentNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                shipment.setId(resultSet.getLong("id"));
                shipment.setCost(resultSet.getDouble("cost"));
                shipment.setOrderId(resultSet.getLong("order_id"));
                shipment.setState(resultSet.getString("state"));
                shipment.setDeliveryWindowId(resultSet.getLong("delivery_window_id"));
                shipment.setRetailerId(resultSet.getLong("retailer_id"));
                shipment.setStoreId(resultSet.getInt("store_id"));
                shipment.setTotalWeight(resultSet.getDouble("total_weight"));
                shipment.setTotal(resultSet.getDouble("total"));
                shipment.setItemCount(resultSet.getInt("item_count"));
                shipment.setTotalQuantity(resultSet.getInt("total_quantity"));
                shipment.setUuid(resultSet.getString("uuid"));
                shipment.setInvoiceNumber(resultSet.getString("invoice_number"));
                shipment.setInvoiceTotal(resultSet.getDouble("invoice_total"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return shipment;
    }
}
