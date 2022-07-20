package ru.instamart.jdbc.dao.shifts;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.shifts.ShiftsEntity;
import ru.instamart.jdbc.util.dispatch.ConnectionPgSQLShiftsManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

@Slf4j
public class ShiftsDao extends AbstractDao<Long, ShiftsEntity> {
    public static final ShiftsDao INSTANCE = new ShiftsDao();
    private static final String SQL_UPDATE_READY_TO_START = "UPDATE shifts SET state='ready_to_start' WHERE id=?";

    public boolean updateState(int id) {
        try (Connection connect = ConnectionPgSQLShiftsManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(SQL_UPDATE_READY_TO_START)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShiftsManager. Error: " + e.getMessage());
        }
        return false;
    }
}
