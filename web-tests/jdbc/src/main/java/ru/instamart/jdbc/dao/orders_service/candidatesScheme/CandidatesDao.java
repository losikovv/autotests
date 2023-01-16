package ru.instamart.jdbc.dao.orders_service.candidatesScheme;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.order_service.candidatesScheme.CandidatesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.testng.Assert.fail;

public class CandidatesDao extends AbstractDao<Long, CandidatesEntity> {

    public static final CandidatesDao INSTANCE = new CandidatesDao();
    private final String SELECT_SQL = "SELECT %s FROM candidates.candidates";

    public CandidatesEntity getCandidateByUuid(String candidateUuid) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ORDER).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE uuid = ?::uuid")) {
            preparedStatement.setString(1, candidateUuid);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    final var candidatesEntity = new CandidatesEntity();
                    candidatesEntity.setUuid(resultSet.getString("uuid"));
                    candidatesEntity.setFullName(resultSet.getString("full_name"));
                    candidatesEntity.setPhone(resultSet.getString("phone"));
                    candidatesEntity.setTransport(resultSet.getString("transport"));
                    candidatesEntity.setActive(resultSet.getBoolean("active"));
                    candidatesEntity.setEmploymentType(resultSet.getInt("employment_type"));
                    candidatesEntity.setCreatedAt(resultSet.getString("created_at"));
                    candidatesEntity.setUpdatedAt(resultSet.getString("updated_at"));
                    candidatesEntity.setDeletedAt(resultSet.getString("deleted_at"));
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
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ORDER).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE uuid in (" + collect + ")")) {
            AtomicInteger index = new AtomicInteger(1);
            for (String uuid : candidateUuid) preparedStatement.setString(index.getAndIncrement(), uuid);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    final var candidatesEntity = new CandidatesEntity();
                    candidatesEntity.setUuid(resultSet.getString("uuid"));
                    candidatesEntity.setFullName(resultSet.getString("full_name"));
                    candidatesEntity.setPhone(resultSet.getString("phone"));
                    candidatesEntity.setTransport(resultSet.getString("transport"));
                    candidatesEntity.setActive(resultSet.getBoolean("active"));
                    candidatesEntity.setEmploymentType(resultSet.getInt("employment_type"));
                    candidatesEntity.setCreatedAt(resultSet.getString("created_at"));
                    candidatesEntity.setUpdatedAt(resultSet.getString("updated_at"));
                    candidatesEntity.setDeletedAt(resultSet.getString("deleted_at"));
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
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ORDER).getConnection();
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
