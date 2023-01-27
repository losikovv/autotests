package ru.instamart.jdbc.dao.workflow;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.workflow.SegmentsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.fail;

public class SegmentsDao extends AbstractDao<Long, SegmentsEntity> {

    public static final SegmentsDao INSTANCE = new SegmentsDao();
    private final String SELECT_SQL = "SELECT %s FROM segments";

    public List<SegmentsEntity> getSegmentsByWorkflowUuid(String workflowUuid) {
        final var segmentsEntities = new ArrayList<SegmentsEntity>();
        try (final var connect = ConnectionManager.getDataSource(Db.PG_WORKFLOW).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "s.*") + " s JOIN assignments a ON s.workflow_id = a.workflow_id " +
                     "WHERE a.uuid = ?")) {
            preparedStatement.setString(1, workflowUuid);
            try (final var resultSet = preparedStatement.executeQuery()) {
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
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLWorkflowManager. Error: " + e.getMessage());
        }
        return segmentsEntities;
    }

    public SegmentsEntity getSegmentsByPerformerUuid(String performerUuid, Integer positionId) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_WORKFLOW).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "s.*") + " s JOIN assignments a ON s.workflow_id = a.workflow_id " +
                     "WHERE a.uuid = (select uuid from assignments ass where performer_uuid = ? order by ass.updated_at desc limit 1) and s.position = ? limit 1")) {
            preparedStatement.setString(1, performerUuid);
            preparedStatement.setInt(2, positionId);
            try (final var resultSet = preparedStatement.executeQuery()) {
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
                    return segmentsEntity;
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLWorkflowManager. Error: " + e.getMessage());
        }
        return null;
    }
}
