package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.StoreSchedulesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class StoreSchedulesDao extends AbstractDao<Long, StoreSchedulesEntity> {

    public static final StoreSchedulesDao INSTANCE = new StoreSchedulesDao();

    private final String DELETE_SQL = "DELETE FROM store_schedules ";

    public boolean deleteByStoreId(Integer storeId) {
        int result = 0;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + "WHERE store_id = ?")) {
            preparedStatement.setLong(1, storeId);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return result == 1;
    }
}
