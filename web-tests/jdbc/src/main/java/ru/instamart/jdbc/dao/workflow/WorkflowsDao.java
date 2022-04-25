package ru.instamart.jdbc.dao.workflow;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.workflow.WorkflowsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;
import ru.instamart.jdbc.util.dispatch.ConnectionPgSQLWorkflowManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class WorkflowsDao extends AbstractDao<Long, WorkflowsEntity> {

    public static final WorkflowsDao INSTANCE = new WorkflowsDao();
    private final String SELECT_SQL = "SELECT %s FROM workflows";

    @Override
    public Optional<WorkflowsEntity> findById(Long id) {
        WorkflowsEntity workflowsEntity = null;
        try (Connection connect = ConnectionPgSQLWorkflowManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE id = ?")) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                workflowsEntity = new WorkflowsEntity();
                workflowsEntity.setId(resultSet.getLong("id"));
                workflowsEntity.setStatus(resultSet.getInt("status"));
                workflowsEntity.setPlanStartedAt(resultSet.getString("plan_started_at"));
                workflowsEntity.setPlanEndedAt(resultSet.getString("plan_ended_at"));
                workflowsEntity.setShiftId(resultSet.getLong("shift_id"));
                workflowsEntity.setShipments(resultSet.getString("shipments"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLWorkflowManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(workflowsEntity);
    }
}
