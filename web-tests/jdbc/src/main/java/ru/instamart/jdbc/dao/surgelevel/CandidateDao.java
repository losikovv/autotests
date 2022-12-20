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
