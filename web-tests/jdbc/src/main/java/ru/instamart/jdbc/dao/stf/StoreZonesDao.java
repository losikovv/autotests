package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.StoreZonesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.fail;

public class StoreZonesDao extends AbstractDao<Long, StoreZonesEntity> {

    public static final StoreZonesDao INSTANCE = new StoreZonesDao();
    private final String SELECT_SQL = "SELECT %s FROM store_zones";
    private final String DELETE_SQL = "DELETE FROM store_zones";

    public List<Integer> getStoreZonesIDsBySid(int sid) {
        List<Integer> ids = new ArrayList<>();
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "id") +
                     " WHERE store_id = ?")) {
            preparedStatement.setInt(1, sid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ids.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return ids;
    }

    public void deleteStoreZoneByStoreId(Integer storeId) {
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE store_id = ?")) {
            preparedStatement.setLong(1, storeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}