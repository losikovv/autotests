package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.SpreeTaxonomiesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SpreeTaxonomiesDao extends AbstractDao<Long, SpreeTaxonomiesEntity> {

    public static final SpreeTaxonomiesDao INSTANCE = new SpreeTaxonomiesDao();
    private final String SELECT_SQL = "SELECT * FROM spree_taxonomies";

    public Long getIdByName(String name) {
        Long id = null;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "id") + " WHERE name = ?")) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                id = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return id;
    }

    @Override
    public Optional<SpreeTaxonomiesEntity> findById(Long id) {
        SpreeTaxonomiesEntity spreeTaxonomiesEntity = null;
        var sql = String.format(SELECT_SQL, "*") + " WHERE id = ?";
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                spreeTaxonomiesEntity = new SpreeTaxonomiesEntity();
                spreeTaxonomiesEntity.setId(resultSet.getLong("id"));
                spreeTaxonomiesEntity.setName(resultSet.getString("name"));
                spreeTaxonomiesEntity.setPosition(resultSet.getInt("position"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(spreeTaxonomiesEntity);
    }
}
