package ru.instamart.jdbc.dao.orders_service;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.order_service.OrdersEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public Optional<OrdersEntity> findById(String shipmentUuid) {
        OrdersEntity ordersEntity = null;
        try (Connection connect = ConnectionManager.getConnection(Db.PG_ORDER);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE shipment_uuid = ?")) {
            preparedStatement.setString(1, shipmentUuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                ordersEntity = new OrdersEntity();
                ordersEntity.setPlaceUuid(resultSet.getString("place_uuid"));
                ordersEntity.setWeight(resultSet.getInt("weight"));
                ordersEntity.setOrderNumber(resultSet.getString("order_number"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLOrderServiceManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(ordersEntity);
    }

    @Override
    public List<OrdersEntity> findAll() {
        return null;
    }
}
