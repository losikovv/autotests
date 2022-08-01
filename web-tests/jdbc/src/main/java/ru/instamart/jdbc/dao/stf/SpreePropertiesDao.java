package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreePropertiesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreePropertiesDao extends AbstractDao<Long, SpreePropertiesEntity> {
    public static final SpreePropertiesDao INSTANCE = new SpreePropertiesDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_properties";

    public SpreePropertiesEntity getByName(final String name) {
        SpreePropertiesEntity type = new SpreePropertiesEntity();
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE name = ?")) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                type.setId(resultSet.getLong("id"));
                type.setName(resultSet.getString("name"));
                type.setCreatedAt(resultSet.getString("created_at"));
                type.setUpdatedAt(resultSet.getString("updated_at"));
                type.setPresentation(resultSet.getString("presentation"));

            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return type;
    }

    public SpreePropertiesEntity getById(final Long id) {
        SpreePropertiesEntity type = new SpreePropertiesEntity();
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                type.setId(resultSet.getLong("id"));
                type.setName(resultSet.getString("name"));
                type.setCreatedAt(resultSet.getString("created_at"));
                type.setUpdatedAt(resultSet.getString("updated_at"));
                type.setPresentation(resultSet.getString("presentation"));

            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return type;
    }
}
