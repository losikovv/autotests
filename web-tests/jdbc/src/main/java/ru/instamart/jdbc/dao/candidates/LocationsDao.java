package ru.instamart.jdbc.dao.candidates;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.candidates.LocationsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;

import static org.testng.Assert.fail;

public class LocationsDao extends AbstractDao<Long, LocationsEntity> {
    public static final LocationsDao INSTANCE = new LocationsDao();

    public LocationsEntity getLastByUUID(final String candidateUuid) {
        LocationsEntity locationsEntity = null;
        try (final var connect = ConnectionManager.getDataSource(Db.PG_CANDIDATE).getConnection();
             final var preparedStatement = connect.prepareStatement("SELECT * FROM locations WHERE candidate_uuid = ? LIMIT 1")) {
            preparedStatement.setString(1, candidateUuid);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    locationsEntity = new LocationsEntity();
                    locationsEntity.setId(resultSet.getLong("id"));
                    locationsEntity.setCreatedAt(resultSet.getTimestamp("created_at"));
                    locationsEntity.setCandidateUuid(resultSet.getString("candidate_uuid"));
                    locationsEntity.setPoint(resultSet.getString("point"));
                    locationsEntity.setUpdatedAt(resultSet.getString("updated_at"));
                    locationsEntity.setNotifiedAt(resultSet.getString("notified_at"));
                    locationsEntity.setCoordinates(resultSet.getString("coordinates"));
                    locationsEntity.setFakeGpsAppDetected(resultSet.getBoolean("is_fake_gps_app_detected"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLCandidateManager. Error: " + e.getMessage());
        }
        return locationsEntity;
    }
}
