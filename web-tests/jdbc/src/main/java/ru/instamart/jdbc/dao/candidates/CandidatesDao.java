package ru.instamart.jdbc.dao.candidates;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.candidates.CandidatesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.testng.Assert.fail;

public class CandidatesDao extends AbstractDao<Long, CandidatesEntity> {

    public static final CandidatesDao INSTANCE = new CandidatesDao();
    private final String SELECT_SQL = "SELECT %s FROM candidates";

    public CandidatesEntity getCandidateByUuid(String candidateUuid) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_CANDIDATE).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE uuid = ?")) {
            preparedStatement.setString(1, candidateUuid);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    final var candidatesEntity = new CandidatesEntity();
                    candidatesEntity.setId(resultSet.getLong("id"));
                    candidatesEntity.setActive(resultSet.getBoolean("active"));
                    candidatesEntity.setFullName(resultSet.getString("full_name"));
                    candidatesEntity.setTransport(resultSet.getString("transport"));
                    return candidatesEntity;
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLCandidateManager. Error: " + e.getMessage());
        }
        return null;
    }

    public CandidatesEntity getCandidateInUuid(List<String> candidateUuid) {
        String collect = candidateUuid.stream()
                .map(item -> "?")
                .collect(Collectors.joining(" ,"));
        try (final var connect = ConnectionManager.getDataSource(Db.PG_CANDIDATE).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE uuid in (" + collect + ")")) {
            AtomicInteger index = new AtomicInteger(1);
            for (String uuid : candidateUuid) preparedStatement.setString(index.getAndIncrement(), uuid);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    final var candidatesEntity = new CandidatesEntity();
                    candidatesEntity.setId(resultSet.getLong("id"));
                    candidatesEntity.setActive(resultSet.getBoolean("active"));
                    candidatesEntity.setFullName(resultSet.getString("full_name"));
                    candidatesEntity.setTransport(resultSet.getString("transport"));
                    return candidatesEntity;
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLCandidateManager. Error: " + e.getMessage());
        }
        return null;
    }

    public String getCandidateUuidByStatus(boolean status) {
        String uuid = null;
        try (final var connect = ConnectionManager.getDataSource(Db.PG_CANDIDATE).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "uuid") + " WHERE active = ? LIMIT 1")) {
            preparedStatement.setBoolean(1, status);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    uuid = resultSet.getString("uuid");
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLManagerService. Error: " + e.getMessage());
        }
        return uuid;
    }
}
