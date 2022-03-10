package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.SpreeFaqGroupsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SpreeFaqGroupsDao extends AbstractDao<Long, SpreeFaqGroupsEntity> {

    public static final SpreeFaqGroupsDao INSTANCE = new SpreeFaqGroupsDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_faq_groups";

    public Long getIdByName(String name) {
        Long id = null;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "id") + " WHERE name = ?")) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return id;
    }

    @Override
    public Optional<SpreeFaqGroupsEntity> findById(Long id) {
        SpreeFaqGroupsEntity spreeFaqGroupsEntity = null;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE id = ?")) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                spreeFaqGroupsEntity = new SpreeFaqGroupsEntity();
                spreeFaqGroupsEntity.setId(resultSet.getLong("id"));
                spreeFaqGroupsEntity.setName(resultSet.getString("name"));
                spreeFaqGroupsEntity.setPosition(resultSet.getInt("position"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(spreeFaqGroupsEntity);
    }
}
