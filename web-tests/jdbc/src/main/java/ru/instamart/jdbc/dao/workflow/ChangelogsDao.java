package ru.instamart.jdbc.dao.workflow;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.workflow.ChangelogsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class ChangelogsDao extends AbstractDao<Long, ChangelogsEntity> {

    public static final ChangelogsDao INSTANCE = new ChangelogsDao();
    private final String SELECT_SQL = "SELECT %s FROM changelogs";

    public ChangelogsEntity getChangelogByWorkflowId(long workflowId) {
        ChangelogsEntity changelogsEntity = null;
        try (Connection connect = ConnectionManager.getConnection(Db.PG_WORKFLOW);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE workflow_id = ?")) {
            preparedStatement.setLong(1, workflowId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                changelogsEntity = new ChangelogsEntity();
                changelogsEntity.setId(resultSet.getLong("id"));
                changelogsEntity.setSegmentId(resultSet.getLong("segment_id"));
                changelogsEntity.setTimingsBefore(resultSet.getString("timings_before"));
                changelogsEntity.setTimingsAfter(resultSet.getString("timings_after"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLWorkflowManager. Error: " + e.getMessage());
        }
        return changelogsEntity;
    }
}
