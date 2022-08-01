package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.FlipperGatesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class FlipperGatesDao extends AbstractDao<Long, FlipperGatesEntity> {

    public static final FlipperGatesDao INSTANCE = new FlipperGatesDao();
    private static final String SELECT_SQL = "SELECT %s FROM flipper_gates";
    private static final String INSERT_SQL = "INSERT INTO flipper_gates";
    private final String DELETE_SQL = "DELETE FROM flipper_gates";

    public FlipperGatesEntity getFlipperByKey(String featureKey) {
        FlipperGatesEntity flipper = new FlipperGatesEntity();
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE feature_key = ?")) {
            preparedStatement.setString(1, featureKey);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                flipper.setId(resultSet.getLong("id"));
                flipper.setKey(resultSet.getString("key"));
                flipper.setValue(resultSet.getString("value"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return flipper;
    }

    public void addFlipper(String featureKey, String date) {
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(INSERT_SQL + " (feature_key, `key`, value, created_at, updated_at) VALUES(?, 'boolean', 'true', ?, ?)")) {
            preparedStatement.setString(1, featureKey);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, date);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }

    public void deleteFlipper(String featureKey) {
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE feature_key = ?")) {
            preparedStatement.setString(1, featureKey);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
