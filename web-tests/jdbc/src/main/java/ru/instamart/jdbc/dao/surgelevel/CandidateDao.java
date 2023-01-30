package ru.instamart.jdbc.dao.surgelevel;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.surgelevel.CandidateEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class CandidateDao extends AbstractDao<String, CandidateEntity> {

    public static final CandidateDao INSTANCE = new CandidateDao();

    public CandidateEntity findCandidate(String candidateUuid) {
        final var result = new CandidateEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SURGE_LEVEL).getConnection();
             final var preparedStatement = connect.prepareStatement(" SELECT * FROM candidate WHERE id = ?::uuid ")) {
            preparedStatement.setString(1, candidateUuid);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result.setId(resultSet.getString("id"));
                    result.setRole(resultSet.getInt("role"));
                    result.setLat(resultSet.getFloat("lat"));
                    result.setLon(resultSet.getFloat("lon"));
                    result.setDeliveryArea(resultSet.getInt("deliveryarea"));
                    result.setBusy(resultSet.getBoolean("busy"));
                    result.setOndemand(resultSet.getBoolean("ondemand"));
                    result.setOnshift(resultSet.getBoolean("onshift"));
                    result.setFakegps(resultSet.getBoolean("fakegps"));
                    result.setCreatedAt(resultSet.getString("created_at"));
                    result.setUpdatedAt(resultSet.getString("updated_at"));
                    result.setExpiredAt(resultSet.getString("expired_at"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLSurgelevelManager. Error: " + e.getMessage());
        }
        return result;
    }

    public boolean updateCandidate(String candidateUuid, Boolean isBusy, String updatedAt) {
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_SURGE_LEVEL).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(" UPDATE candidate SET busy = ?, updated_at = ?::timestamp " +
                     "WHERE id = ?::uuid")) {
            preparedStatement.setBoolean(1, isBusy);
            preparedStatement.setString(2, updatedAt);
            preparedStatement.setString(3, candidateUuid);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLSurgelevelManager. Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Optional<CandidateEntity> findById(String id) {
        return Optional.empty();
    }
}
