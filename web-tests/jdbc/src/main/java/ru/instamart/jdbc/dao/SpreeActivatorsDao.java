package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.SpreeActivatorsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SpreeActivatorsDao extends AbstractDao<Long, SpreeActivatorsEntity> {

    public static final SpreeActivatorsDao INSTANCE = new SpreeActivatorsDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_activators";

    @Override
    public Optional<SpreeActivatorsEntity> findById(Long id) {
        SpreeActivatorsEntity spreeActivatorsEntity = null;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE id = ?")) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                spreeActivatorsEntity = new SpreeActivatorsEntity();
                spreeActivatorsEntity.setId(resultSet.getLong("id"));
                spreeActivatorsEntity.setName(resultSet.getString("name"));
                spreeActivatorsEntity.setType(resultSet.getString("type"));
                spreeActivatorsEntity.setEventName(resultSet.getString("event_name"));
                spreeActivatorsEntity.setServiceComment(resultSet.getString("service_comment"));
                spreeActivatorsEntity.setPrefix(resultSet.getString("prefix"));
                spreeActivatorsEntity.setDescription(resultSet.getString("description"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(spreeActivatorsEntity);
    }
}
