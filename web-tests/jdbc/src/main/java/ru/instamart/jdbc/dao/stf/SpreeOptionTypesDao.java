package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeOptionTypesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreeOptionTypesDao extends AbstractDao<Long, SpreeOptionTypesEntity> {

    public static final SpreeOptionTypesDao INSTANCE = new SpreeOptionTypesDao();
    private static final String SELECT_SQL = "SELECT * FROM spree_option_types";

    public SpreeOptionTypesEntity getByName(String typeName) {
        SpreeOptionTypesEntity type = new SpreeOptionTypesEntity();
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "t.id, t.name, t.presentation, v.name as valueName, v.presentation as valuePresentation") +
                     " t JOIN spree_option_values v ON t.id = v.option_type_id WHERE t.name = ?")) {
            preparedStatement.setString(1, typeName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                type.setId(resultSet.getLong("id"));
                type.setName(resultSet.getString("name"));
                type.setCreatedAt(resultSet.getString("created_at"));
                type.setUpdatedAt(resultSet.getString("updated_at"));
                type.setPosition(resultSet.getString("position"));
                type.setPresentation(resultSet.getString("presentation"));

                type.setValueName(resultSet.getString("valueName"));
                type.setValuePresentation(resultSet.getString("valuePresentation"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return type;
    }

    public SpreeOptionTypesEntity getById(String typeId) {
        SpreeOptionTypesEntity type = new SpreeOptionTypesEntity();
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "t.id, t.name, t.presentation, v.name as valueName, v.presentation as valuePresentation") +
                     " t JOIN spree_option_values v ON t.id = v.option_type_id WHERE t.id = ?")) {
            preparedStatement.setString(1, typeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                type.setId(resultSet.getLong("id"));
                type.setName(resultSet.getString("name"));
                type.setCreatedAt(resultSet.getString("created_at"));
                type.setUpdatedAt(resultSet.getString("updated_at"));
                type.setPosition(resultSet.getString("position"));
                type.setPresentation(resultSet.getString("presentation"));

                type.setValueName(resultSet.getString("valueName"));
                type.setValuePresentation(resultSet.getString("valuePresentation"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return type;
    }

}
