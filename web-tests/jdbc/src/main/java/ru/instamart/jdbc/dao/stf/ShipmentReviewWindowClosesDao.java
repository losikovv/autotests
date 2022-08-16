package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.ShipmentReviewWindowClosesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class ShipmentReviewWindowClosesDao extends AbstractDao<Long, ShipmentReviewWindowClosesEntity> {

    public static final ShipmentReviewWindowClosesDao INSTANCE = new ShipmentReviewWindowClosesDao();

    public void updateNumberOfCloses(Integer number, Integer shipmentId) {
        try (Connection connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement("UPDATE shipment_review_window_closes SET number = ? WHERE shipment_id = ?")) {
            preparedStatement.setInt(1, number);
            preparedStatement.setInt(2, shipmentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
