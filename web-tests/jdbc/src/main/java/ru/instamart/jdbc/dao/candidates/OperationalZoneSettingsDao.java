package ru.instamart.jdbc.dao.candidates;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.candidates.OperationalZoneSettingsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class OperationalZoneSettingsDao extends AbstractDao<Long, OperationalZoneSettingsEntity> {

    public static final OperationalZoneSettingsDao INSTANCE = new OperationalZoneSettingsDao();
    private final String SELECT_SQL = "SELECT %s FROM operational_zone_settings";

    public OperationalZoneSettingsEntity getOperationalZoneSettings (Integer zoneId) {
        OperationalZoneSettingsEntity operationalZoneSettingsEntity = null;
        try (Connection connect = ConnectionManager.getConnection(Db.PG_CANDIDATE);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE operational_zone_id = ?")) {
            preparedStatement.setInt(1, zoneId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                operationalZoneSettingsEntity = new OperationalZoneSettingsEntity();
                operationalZoneSettingsEntity.setId(resultSet.getLong("id"));
                operationalZoneSettingsEntity.setOperationalZoneId(resultSet.getInt("operational_zone_id"));
                operationalZoneSettingsEntity.setSurgedShiftThreshold(resultSet.getInt("surged_shift_threshold"));
                operationalZoneSettingsEntity.setNormalShiftThreshold(resultSet.getInt("normal_shift_threshold"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLCandidateManager. Error: " + e.getMessage());
        }
        return operationalZoneSettingsEntity;
    }
}
