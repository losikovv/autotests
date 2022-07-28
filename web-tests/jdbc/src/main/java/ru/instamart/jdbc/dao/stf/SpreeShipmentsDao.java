package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeShipmentsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreeShipmentsDao extends AbstractDao<Long, SpreeShipmentsEntity> {

    public static final SpreeShipmentsDao INSTANCE = new SpreeShipmentsDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_shipments";
    private final String DELETE_SQL = "DELETE FROM spree_shipments";
    private final String UPDATE_SQL = "UPDATE spree_shipments";

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

    public void updateShipmentState(String shipmentNumber, String state) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_SQL + " SET state = ?  WHERE number = ?")) {
            preparedStatement.setString(1, state);
            preparedStatement.setString(2, shipmentNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }

    public void updateShipmentOrderByNumber(long orderId, String shipmentNumber) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_SQL + " SET order_id = ? WHERE number = ?")) {
            preparedStatement.setLong(1, orderId);
            preparedStatement.setString(2, shipmentNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }

    public SpreeShipmentsEntity getShipmentOfAnotherUser(long userId) {
        SpreeShipmentsEntity shipment = new SpreeShipmentsEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " ss JOIN spree_orders so ON ss.order_id = so.id WHERE so.user_id != ? ORDER BY so.id DESC LIMIT 1")) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                shipment.setId(resultSet.getLong("id"));
                shipment.setNumber(resultSet.getString("number"));
                shipment.setState(resultSet.getString("state"));
                shipment.setDeliveryWindowId(resultSet.getLong("delivery_window_id"));
                shipment.setUuid(resultSet.getString("uuid"));
                shipment.setStoreId(resultSet.getInt("store_id"));
                shipment.setTotalWeight(resultSet.getDouble("total_weight"));
                shipment.setTotal(resultSet.getDouble("total"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return shipment;
    }

    public Integer updateShipmentsByNumber(String shippedAt, String shipmentsNumber) {
        try (Connection connection = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL + " SET shipped_at = ? WHERE number = ?");
        ) {
            preparedStatement.setString(1, shippedAt);
            preparedStatement.setString(2, shipmentsNumber);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return null;
    }
}
