package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.SpreePreferencesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

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
        try (Connection connect = ConnectionMySQLManager.get();
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
