package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreePreferencesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreePreferencesDao extends AbstractDao<Long, SpreePreferencesEntity> {

    public static final SpreePreferencesDao INSTANCE = new SpreePreferencesDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_preferences";

    public String getPreferencesKeyByValue(String key) {
        String value = null;
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "p.value") +
                     " p WHERE p.key LIKE ?")) {
            preparedStatement.setString(1, String.format("%%%s%%", key));
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            value = resultSet.getString("p.value");
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return value;
    }
}
