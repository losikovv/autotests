package ru.instamart.jdbc.dao.orders_service;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.order_service.OrdersEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class OrdersDao implements Dao<String, OrdersEntity> {
    public static final OrdersDao INSTANCE = new OrdersDao();
    private final String SELECT_SQL = "SELECT %s FROM orders";

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public OrdersEntity save(OrdersEntity ticket) {
        return null;
    }

    @Override
    public void update(OrdersEntity ticket) {

    }

    @Override
    public Optional<OrdersEntity> findById(String id) {
        return Optional.empty();
    }

    public OrdersEntity findByShipmentUuid(String shipmentUuid) {
        OrdersEntity ordersEntity = null;
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ORDER).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE shipment_uuid = ?::uuid LIMIT 1")) {
            preparedStatement.setString(1, shipmentUuid);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    ordersEntity = new OrdersEntity();
                    ordersEntity.setShipmentUuid(resultSet.getString("shipment_uuid"));
                    ordersEntity.setPlaceUuid(resultSet.getString("place_uuid"));
                    ordersEntity.setWeight(resultSet.getDouble("weight"));
                    ordersEntity.setClientLocation(resultSet.getString("client_location"));
                    ordersEntity.setStatus(resultSet.getString("status"));
                    ordersEntity.setType(resultSet.getString("type"));
                    ordersEntity.setItemCount(resultSet.getInt("item_count"));
                    ordersEntity.setDeliveryPromiseUpperDttm(resultSet.getString("delivery_promise_upper_dttm"));
                    ordersEntity.setCreatedAt(resultSet.getString("created_at"));
                    ordersEntity.setTimeToThrowDttm(resultSet.getString("time_to_throw_dttm"));
                    ordersEntity.setStfOrderUuid(resultSet.getString("stf_order_uuid"));
                    ordersEntity.setUpdatedAt(resultSet.getString("updated_at"));
                    ordersEntity.setAssemblyTimeMin(resultSet.getInt("assembly_time_min"));
                    ordersEntity.setStraightDistanceToClientMin(resultSet.getInt("straight_distance_to_client_min"));
                    ordersEntity.setAssembly(resultSet.getString("assembly"));
                    ordersEntity.setDelivery(resultSet.getString("delivery"));
                    ordersEntity.setSettings(resultSet.getString("settings"));
                    ordersEntity.setPlaceLocation(resultSet.getString("place_location"));
                    ordersEntity.setSent(resultSet.getBoolean("sent"));
                    ordersEntity.setDeletedAt(resultSet.getString("deleted_at"));
                    ordersEntity.setDeliveryPromiseLowerDttm(resultSet.getString("delivery_promise_lower_dttm"));
                    ordersEntity.setOrderNumber(resultSet.getString("order_number"));
                    ordersEntity.setItemsTotalAmount(resultSet.getDouble("items_total_amount"));
                    ordersEntity.setIsNew(resultSet.getBoolean("is_new"));
                    ordersEntity.setOrderStatus(resultSet.getString("order_status"));
                    ordersEntity.setLastRedispatchTime(resultSet.getString("last_redispatch_time"));
                    ordersEntity.setOrderUuid(resultSet.getString("order_uuid"));
                    ordersEntity.setShippingMethod(resultSet.getString("shipping_method"));
                    ordersEntity.setSendToDispatchCount(resultSet.getInt("send_to_dispatch_count"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLOrderServiceManager. Error: " + e.getMessage());
        }
        return ordersEntity;
    }

    public OrdersEntity findByOrderUuid(String shipmentUuid) {
        OrdersEntity ordersEntity = null;
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ORDER).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE order_uuid = ?::uuid LIMIT 1")) {
            preparedStatement.setString(1, shipmentUuid);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    ordersEntity = new OrdersEntity();
                    ordersEntity.setShipmentUuid(resultSet.getString("shipment_uuid"));
                    ordersEntity.setPlaceUuid(resultSet.getString("place_uuid"));
                    ordersEntity.setWeight(resultSet.getDouble("weight"));
                    ordersEntity.setClientLocation(resultSet.getString("client_location"));
                    ordersEntity.setStatus(resultSet.getString("status"));
                    ordersEntity.setType(resultSet.getString("type"));
                    ordersEntity.setItemCount(resultSet.getInt("item_count"));
                    ordersEntity.setDeliveryPromiseUpperDttm(resultSet.getString("delivery_promise_upper_dttm"));
                    ordersEntity.setCreatedAt(resultSet.getString("created_at"));
                    ordersEntity.setTimeToThrowDttm(resultSet.getString("time_to_throw_dttm"));
                    ordersEntity.setStfOrderUuid(resultSet.getString("stf_order_uuid"));
                    ordersEntity.setUpdatedAt(resultSet.getString("updated_at"));
                    ordersEntity.setAssemblyTimeMin(resultSet.getInt("assembly_time_min"));
                    ordersEntity.setStraightDistanceToClientMin(resultSet.getInt("straight_distance_to_client_min"));
                    ordersEntity.setAssembly(resultSet.getString("assembly"));
                    ordersEntity.setDelivery(resultSet.getString("delivery"));
                    ordersEntity.setSettings(resultSet.getString("settings"));
                    ordersEntity.setPlaceLocation(resultSet.getString("place_location"));
                    ordersEntity.setSent(resultSet.getBoolean("sent"));
                    ordersEntity.setDeletedAt(resultSet.getString("deleted_at"));
                    ordersEntity.setDeliveryPromiseLowerDttm(resultSet.getString("delivery_promise_lower_dttm"));
                    ordersEntity.setOrderNumber(resultSet.getString("order_number"));
                    ordersEntity.setItemsTotalAmount(resultSet.getDouble("items_total_amount"));
                    ordersEntity.setIsNew(resultSet.getBoolean("is_new"));
                    ordersEntity.setOrderStatus(resultSet.getString("order_status"));
                    ordersEntity.setLastRedispatchTime(resultSet.getString("last_redispatch_time"));
                    ordersEntity.setOrderUuid(resultSet.getString("order_uuid"));
                    ordersEntity.setShippingMethod(resultSet.getString("shipping_method"));
                    ordersEntity.setSendToDispatchCount(resultSet.getInt("send_to_dispatch_count"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLOrderServiceManager. Error: " + e.getMessage());
        }
        return ordersEntity;
    }

    @Override
    public List<OrdersEntity> findAll() {
        return null;
    }
}
