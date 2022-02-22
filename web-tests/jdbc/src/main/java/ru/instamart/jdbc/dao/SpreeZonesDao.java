package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.SpreeZonesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SpreeZonesDao extends AbstractDao<Long, SpreeZonesEntity> {

    public static final SpreeZonesDao INSTANCE = new SpreeZonesDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_zones";
    private final String DELETE_SQL = "DELETE FROM spree_zones";

    public SpreeZonesEntity getZoneByDescription(String zoneDescription) {
        SpreeZonesEntity zone = new SpreeZonesEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE description = ?")) {
            preparedStatement.setString(1, zoneDescription);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                zone.setId(resultSet.getLong("id"));
                zone.setName(resultSet.getString("name"));
                zone.setZoneMembersCount(resultSet.getInt("zone_members_count"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return zone;
    }

    @Override
    public Optional<SpreeZonesEntity> findById(Long id) {
        SpreeZonesEntity spreeZonesEntity = null;
        var sql = String.format(SELECT_SQL, "*") + " WHERE id = ?";
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                spreeZonesEntity = new SpreeZonesEntity();
                spreeZonesEntity.setId(resultSet.getLong("id"));
                spreeZonesEntity.setName(resultSet.getString("name"));
                spreeZonesEntity.setDescription(resultSet.getString("description"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(spreeZonesEntity);
    }

    public void deleteZonesByName(String zoneName) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE name LIKE ?")) {
            preparedStatement.setString(1,String.format("%s%%", zoneName));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
