package ru.instamart.jdbc.dao.workflow;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.workflow.WorkflowsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class WorkflowsDao extends AbstractDao<Long, WorkflowsEntity> {

    public static final WorkflowsDao INSTANCE = new WorkflowsDao();
    private final String SELECT_SQL = "SELECT %s FROM workflows";
    private final String UPDATE_SQL = "UPDATE workflows SET";

    @Override
    public Optional<WorkflowsEntity> findById(Long id) {
        WorkflowsEntity workflowsEntity = null;
        try (final var connect = ConnectionManager.getDataSource(Db.PG_WORKFLOW).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE id = ?")) {
            preparedStatement.setObject(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    workflowsEntity = new WorkflowsEntity();
                    workflowsEntity.setId(resultSet.getLong("id"));
                    workflowsEntity.setStatus(resultSet.getInt("status"));
                    workflowsEntity.setPlanStartedAt(resultSet.getString("plan_started_at"));
                    workflowsEntity.setPlanEndedAt(resultSet.getString("plan_ended_at"));
                    workflowsEntity.setShiftId(resultSet.getLong("shift_id"));
                    workflowsEntity.setShipments(resultSet.getString("shipments"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLWorkflowManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(workflowsEntity);
    }

    public void updateStatus(int status, long workflowId) {
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_WORKFLOW).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_SQL + " status = ? WHERE id = ?")) {
            preparedStatement.setInt(1, status);
            preparedStatement.setLong(2, workflowId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLWorkflowManager. Error: " + e.getMessage());
        }
    }
}
