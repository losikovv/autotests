package ru.instamart.jdbc.dao.orders_service.publicScheme;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.order_service.publicScheme.JobsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class JobsDao implements Dao<String, JobsEntity> {
    public static final JobsDao INSTANCE = new JobsDao();
    private final String SELECT_SQL = "SELECT %s FROM public.jobs";


    public List<JobsEntity> findByShipmentUuid(String shipmentUuid) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ORDER).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE shipment_uuid = ?::uuid")) {
            preparedStatement.setString(1, shipmentUuid);
            try (final var resultSet = preparedStatement.executeQuery()) {
                List<JobsEntity> jobs = new ArrayList<>();
                while (resultSet.next()) {
                    final var job = new JobsEntity();
                    job.setJobId(resultSet.getString("job_id"));
                    job.setJobType(resultSet.getString("job_type"));
                    job.setStatus(resultSet.getString("status"));
                    job.setMeta(resultSet.getString("meta"));
                    job.setCreatedAt(resultSet.getTimestamp("created_at"));
                    job.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    job.setDeletedAt(resultSet.getTimestamp("deleted_at"));
                    job.setShipmentUUID(resultSet.getString("shipment_uuid"));
                    jobs.add(job);
                }
                return jobs;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLOrderServiceManager. Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public JobsEntity save(JobsEntity ticket) {
        return null;
    }

    @Override
    public void update(JobsEntity ticket) {

    }

    @Override
    public Optional<JobsEntity> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<JobsEntity> findAll() {
        return null;
    }
}
