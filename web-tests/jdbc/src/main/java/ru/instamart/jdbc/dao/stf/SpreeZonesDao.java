package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeZonesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SpreeZonesDao extends AbstractDao<Long, SpreeZonesEntity> {

    public static final SpreeZonesDao INSTANCE = new SpreeZonesDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_zones";
    private final String DELETE_SQL = "DELETE FROM spree_zones";

    public SpreeZonesEntity getZoneByDescription(String zoneDescription) {
        final var zone = new SpreeZonesEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE description = ?")) {
            preparedStatement.setString(1, zoneDescription);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    zone.setId(resultSet.getLong("id"));
                    zone.setName(resultSet.getString("name"));
                    zone.setZoneMembersCount(resultSet.getInt("zone_members_count"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return zone;
    }

    @Override
    public Optional<SpreeZonesEntity> findById(Long id) {
        SpreeZonesEntity spreeZonesEntity = null;
        var sql = String.format(SELECT_SQL, "*") + " WHERE id = ?";
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    spreeZonesEntity = new SpreeZonesEntity();
                    spreeZonesEntity.setId(resultSet.getLong("id"));
                    spreeZonesEntity.setName(resultSet.getString("name"));
                    spreeZonesEntity.setDescription(resultSet.getString("description"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(spreeZonesEntity);
    }

    public void deleteZonesByName(String zoneName) {
        try (Connection connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE name LIKE ?")) {
            preparedStatement.setString(1,String.format("%s%%", zoneName));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
