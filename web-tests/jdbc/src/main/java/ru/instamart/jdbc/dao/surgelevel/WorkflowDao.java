package ru.instamart.jdbc.dao.surgelevel;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.surgelevel.WorkflowEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class WorkflowDao extends AbstractDao<Integer, WorkflowEntity> {

    public static final WorkflowDao INSTANCE = new WorkflowDao();

    public WorkflowEntity findWorkflowByCandidateId(String candidateUuid) {
        final var result = new WorkflowEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SURGE_LEVEL).getConnection();
             final var preparedStatement = connect.prepareStatement(" SELECT * FROM workflow WHERE candidate_id = ?::uuid ")) {
            preparedStatement.setString(1, candidateUuid);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result.setId(resultSet.getInt("id"));
                    result.setStatus(resultSet.getInt("status"));
                    result.setCandidateId(resultSet.getString("candidate_id"));
                    result.setShiftId(resultSet.getInt("shift_id"));
                    result.setCoordinatesEndLat(resultSet.getFloat("coordinates_end_lat"));
                    result.setCoordinatesEndLon(resultSet.getFloat("coordinates_end_lon"));
                    result.setPlanEndedAt(resultSet.getString("plan_ended_at"));
                    result.setCreatedAt(resultSet.getString("created_at"));
                    result.setUpdatedAt(resultSet.getString("updated_at"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLSurgelevelManager. Error: " + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Optional<WorkflowEntity> findById(Integer id) {
        return Optional.empty();
    }
}
