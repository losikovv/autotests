package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.DeliveryWindowsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class DeliveryWindowsDao extends AbstractDao<Long, DeliveryWindowsEntity> {

    public static final DeliveryWindowsDao INSTANCE = new DeliveryWindowsDao();
    private final String SELECT_SQL = "SELECT %s FROM delivery_windows";


    public int getCount(Integer storeId, String start, String end) {
        int resultCount = 0;
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total") + " WHERE store_id = ? AND kind != 'on_demand' AND starts_at BETWEEN ? AND ?")) {
            preparedStatement.setInt(1, storeId);
            preparedStatement.setString(2, start);
            preparedStatement.setString(3, end);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    resultCount = resultSet.getInt("total");
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return resultCount;
    }

    public void updateDeliveryWindowSettings(Integer id, Integer shipmentsLimit, Integer shipmentsCount, Integer baseItemsCount, Integer totalItemsCount) {
        try (Connection connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement("UPDATE delivery_windows SET shipments_limit = ?, shipments_count = ?, " +
                     "shipment_base_items_count = ?, shipments_total_items_count = ?, " +
                     "state = 'open' WHERE id = ?")) {
            preparedStatement.setInt(1, shipmentsLimit);
            preparedStatement.setInt(2, shipmentsCount);
            preparedStatement.setInt(3, baseItemsCount);
            preparedStatement.setInt(4, totalItemsCount);
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
