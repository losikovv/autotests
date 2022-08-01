package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.ShipmentDelaysEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;
import ru.instamart.kraken.util.ThreadUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.testng.Assert.fail;

public class ShipmentDelaysDao extends AbstractDao<Long, ShipmentDelaysEntity> {

    public static final ShipmentDelaysDao INSTANCE = new ShipmentDelaysDao();
    private final String UPDATE_SQL = "UPDATE shipment_delays";
    private static final String SELECT_SQL = "SELECT %s FROM shipment_delays";

    public void updateDeadlineDate(String deadlineAt, Long shipmentId) {
        AtomicInteger count = new AtomicInteger(0);
        while (count.get() < 30) {
            boolean result = updateDeadlineDateData(deadlineAt, shipmentId);
            if (result)
                break;
            ThreadUtil.simplyAwait(1);
            count.getAndIncrement();
        }
    }

    public void updateNotificationDate(String notificationSentAt, Long shipmentId) {
        AtomicInteger count = new AtomicInteger(0);
        while (count.getAndIncrement() < 40) {
            boolean result = updateNotificationDateData(notificationSentAt, shipmentId);
            if (result)
                break;
            ThreadUtil.simplyAwait(1);
            count.getAndIncrement();
        }
    }

    private boolean updateDeadlineDateData(String deadlineAt, Long shipmentId) {
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_SQL + " SET deadline_at = ? WHERE shipment_id = ?")) {
            preparedStatement.setString(1, deadlineAt);
            preparedStatement.setLong(2, shipmentId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return false;
    }

    private boolean updateNotificationDateData(String notificationSentAt, Long shipmentId) {
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_SQL + " SET notification_sent_at = ?, state = 1 WHERE shipment_id = ?")) {
            preparedStatement.setString(1, notificationSentAt);
            preparedStatement.setLong(2, shipmentId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return false;
    }

    public String getNotificationTimeByShipmentId(Long shipmentId) {
        String notificationSentAt = null;
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "notification_sent_at") +
                     " WHERE shipment_id = ?")) {
            preparedStatement.setLong(1, shipmentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                notificationSentAt = resultSet.getString("notification_sent_at");
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return notificationSentAt;
    }
}
