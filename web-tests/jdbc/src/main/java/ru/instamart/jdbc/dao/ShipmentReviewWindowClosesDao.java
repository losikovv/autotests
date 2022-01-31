package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.ShipmentReviewWindowClosesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class ShipmentReviewWindowClosesDao extends AbstractDao<Long, ShipmentReviewWindowClosesEntity> {

    public static final ShipmentReviewWindowClosesDao INSTANCE = new ShipmentReviewWindowClosesDao();

    public void updateNumberOfCloses(Integer number, Integer shipmentId) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement("UPDATE shipment_review_window_closes SET number = ? WHERE shipment_id = ?")) {
            preparedStatement.setInt(1, number);
            preparedStatement.setInt(2, shipmentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
