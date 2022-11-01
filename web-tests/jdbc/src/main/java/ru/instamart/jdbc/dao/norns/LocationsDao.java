package ru.instamart.jdbc.dao.norns;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.norns.LocationsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;

import static org.testng.Assert.fail;

public class LocationsDao extends AbstractDao<Long, LocationsEntity> {
    public static final LocationsDao INSTANCE = new LocationsDao();

    public LocationsEntity getLastByUUID(final String candidateUuid) {
        LocationsEntity locationsEntity = null;
        try (final var connect = ConnectionManager.getDataSource(Db.PG_NORNS).getConnection();
             final var preparedStatement = connect.prepareStatement("SELECT * FROM locations WHERE user_uuid = ? LIMIT 1")) {
            preparedStatement.setString(1, candidateUuid);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    locationsEntity = new LocationsEntity();
                    locationsEntity.setId(resultSet.getString("id"));
                    locationsEntity.setShopperId(resultSet.getLong("shopper_id"));
                    locationsEntity.setLocation(resultSet.getString("location"));
                    locationsEntity.setTime(resultSet.getLong("time"));
                    locationsEntity.setSpeed(resultSet.getLong("speed"));
                    locationsEntity.setCreatedAt(resultSet.getTimestamp("created_at"));
                    locationsEntity.setUserUuid(resultSet.getString("user_uuid"));
                    locationsEntity.setIsValid(resultSet.getBoolean("is_valid"));
                    locationsEntity.setInvalidReason(resultSet.getString("invalid_reason"));
                    locationsEntity.setIsSended(resultSet.getBoolean("is_sended"));
                    locationsEntity.setIsFakeGpsAppDetected(resultSet.getBoolean("is_fake_gps_app_detected"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLCandidateManager. Error: " + e.getMessage());
        }
        return locationsEntity;
    }
}
