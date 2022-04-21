package ru.instamart.jdbc.dao.candidates;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.candidates.CandidatesEntity;
import ru.instamart.jdbc.util.dispatch.ConnectionPgSQLCandidateManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class CandidatesDao extends AbstractDao<Long, CandidatesEntity> {

    public static final CandidatesDao INSTANCE = new CandidatesDao();
    private final String SELECT_SQL = "SELECT %s FROM candidates";

    public CandidatesEntity getCandidateByUuid(String candidateUuid) {
        CandidatesEntity candidatesEntity = null;
        try (Connection connect = ConnectionPgSQLCandidateManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE uuid = ?")) {
            preparedStatement.setString(1, candidateUuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                candidatesEntity = new CandidatesEntity();
                candidatesEntity.setId(resultSet.getLong("id"));
                candidatesEntity.setActive(resultSet.getBoolean("active"));
                candidatesEntity.setFullName(resultSet.getString("full_name"));
                candidatesEntity.setTransport(resultSet.getString("transport"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLCandidateManager. Error: " + e.getMessage());
        }
        return candidatesEntity;
    }

    public String getCandidateUuidByStatus(boolean status) {
        String uuid = null;
        try (Connection connect = ConnectionPgSQLCandidateManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "uuid") + " WHERE active = ? ORDER BY id DESC LIMIT 1")) {
            preparedStatement.setBoolean(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            uuid = resultSet.getString("uuid");
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLManagerService. Error: " + e.getMessage());
        }
        return uuid;
    }
}
