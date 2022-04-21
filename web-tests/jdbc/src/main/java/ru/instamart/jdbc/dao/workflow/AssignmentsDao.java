package ru.instamart.jdbc.dao.workflow;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.workflow.AssignmentsEntity;
import ru.instamart.jdbc.util.dispatch.ConnectionPgSQLWorkflowManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class AssignmentsDao extends AbstractDao<Long, AssignmentsEntity> {

    public static final AssignmentsDao INSTANCE = new AssignmentsDao();
    private final String SELECT_SQL = "SELECT %s FROM assignments";

    public AssignmentsEntity getAssignmentByWorkflowUuid(String workflowUuid) {
        AssignmentsEntity assignmentsEntity = null;
        try (Connection connect = ConnectionPgSQLWorkflowManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE uuid = ?")) {
            preparedStatement.setString(1, workflowUuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                assignmentsEntity = new AssignmentsEntity();
                assignmentsEntity.setId(resultSet.getLong("id"));
                assignmentsEntity.setWorkflowId(resultSet.getLong("workflow_id"));
                assignmentsEntity.setPostponedParentId(resultSet.getLong("postponed_parent_id"));
                assignmentsEntity.setPerformerUuid(resultSet.getString("performer_uuid"));
                assignmentsEntity.setStatus(resultSet.getInt("status"));
                assignmentsEntity.setShipments(resultSet.getString("shipments"));
                assignmentsEntity.setPostponedParentUuid(resultSet.getString("postponed_parent_uuid"));
                assignmentsEntity.setPlanPayroll(resultSet.getInt("plan_payroll"));
                assignmentsEntity.setPlanPayrollBase(resultSet.getInt("plan_payroll_base"));
                assignmentsEntity.setPlanPayrollBonus(resultSet.getInt("plan_payroll_bonus"));
                assignmentsEntity.setShift(resultSet.getString("shift"));
                assignmentsEntity.setPerformerName(resultSet.getString("performer_name"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLWorkflowManager. Error: " + e.getMessage());
        }
        return assignmentsEntity;
    }
}
