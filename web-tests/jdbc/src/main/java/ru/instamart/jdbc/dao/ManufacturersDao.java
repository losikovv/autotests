package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.ManufacturersEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

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
        try (Connection connect = ConnectionMySQLManager.get();
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
