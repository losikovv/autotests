package ru.instamart.jdbc.dao.workflow;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.workflow.SegmentsEntity;
import ru.instamart.jdbc.util.dispatch.ConnectionPgSQLWorkflowManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.fail;

public class SegmentsDao extends AbstractDao<Long, SegmentsEntity> {

    public static final SegmentsDao INSTANCE = new SegmentsDao();
    private final String SELECT_SQL = "SELECT %s FROM segments";

    public List<SegmentsEntity> getSegmentsByWorkflowUuid(String workflowUuid) {
        List<SegmentsEntity> segmentsEntities = new ArrayList<>();
        try (Connection connect = ConnectionPgSQLWorkflowManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "s.*") + " s JOIN assignments a ON s.workflow_id = a.workflow_id " +
                     "WHERE a.uuid = ?")) {
            preparedStatement.setString(1, workflowUuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                SegmentsEntity segmentsEntity = new SegmentsEntity();
                segmentsEntity.setId(resultSet.getLong("id"));
                segmentsEntity.setWorkflowId(resultSet.getLong("workflow_id"));
                segmentsEntity.setType(resultSet.getInt("type"));
                segmentsEntity.setPosition(resultSet.getInt("position"));
                segmentsEntity.setShipments(resultSet.getString("shipments"));
                segmentsEntity.setDuration(resultSet.getLong("duration"));
                segmentsEntity.setDistance(resultSet.getInt("distance"));
                segmentsEntity.setStoreName(resultSet.getString("store_name"));
                segmentsEntity.setStoreAddress(resultSet.getString("store_address"));
                segmentsEntity.setFactStartedAt(resultSet.getString("fact_started_at"));
                segmentsEntity.setFactEndedAt(resultSet.getString("fact_ended_at"));
                segmentsEntity.setFactLocationStart(resultSet.getString("fact_location_start"));
                segmentsEntity.setFactLocationEnd(resultSet.getString("fact_location_end"));
                segmentsEntity.setPlanEndedAt(resultSet.getString("plan_ended_at"));
                segmentsEntity.setPlanStartedAt(resultSet.getString("plan_started_at"));
                segmentsEntities.add(segmentsEntity);
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLWorkflowManager. Error: " + e.getMessage());
        }
        return segmentsEntities;
    }
}
