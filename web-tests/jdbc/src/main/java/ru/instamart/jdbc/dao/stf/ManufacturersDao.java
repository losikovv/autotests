package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.ManufacturersEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class ManufacturersDao extends AbstractDao<Long, ManufacturersEntity> {

    public static final ManufacturersDao INSTANCE = new ManufacturersDao();
    private final String SELECT_SQL = "SELECT id, name, created_at, updated_at FROM spree_manufacturers";

    public ManufacturersEntity getIdByName(String name) {
        ManufacturersEntity manufacturers = new ManufacturersEntity();
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(SELECT_SQL +
                     " WHERE name = ?")) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                manufacturers.setId(resultSet.getLong("id"));
                manufacturers.setName(resultSet.getString("name"));
                manufacturers.setCreated_at(resultSet.getString("created_at"));
                manufacturers.setUpdated_at(resultSet.getString("updated_at"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return manufacturers;
    }
}
