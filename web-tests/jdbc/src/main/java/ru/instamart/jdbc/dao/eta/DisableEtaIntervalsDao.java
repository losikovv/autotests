package ru.instamart.jdbc.dao.eta;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.eta.DisableEtaIntervalsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;
import static ru.instamart.kraken.util.TimeUtil.*;

public class DisableEtaIntervalsDao implements Dao<Integer, DisableEtaIntervalsEntity> {

    public static final DisableEtaIntervalsDao INSTANCE = new DisableEtaIntervalsDao();
    private final String SELECT_SQL = "SELECT %s FROM disable_eta_intervals ";
    private final String INSERT_SQL = "INSERT INTO disable_eta_intervals ";

    public List<DisableEtaIntervalsEntity> getIntervals(String storeUuid) {
        final var intervalResult = new ArrayList<DisableEtaIntervalsEntity>();

        try (final var connect = ConnectionManager.getDataSource(Db.PG_ETA).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE store_uuid = ?::uuid AND deleted_at IS NULL ORDER BY id DESC ")) {
            preparedStatement.setString(1, storeUuid);
            try (final var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    var disableEtaIntervalsEntity = new DisableEtaIntervalsEntity();
                    disableEtaIntervalsEntity.setId(resultSet.getInt("id"));
                    disableEtaIntervalsEntity.setStoreUuid(resultSet.getString("store_uuid"));
                    disableEtaIntervalsEntity.setIntervalType(resultSet.getString("interval_type"));
                    disableEtaIntervalsEntity.setStartAt(resultSet.getString("start_at"));
                    disableEtaIntervalsEntity.setEndAt(resultSet.getString("end_at"));
                    disableEtaIntervalsEntity.setCreatedAt(resultSet.getString("created_at"));
                    disableEtaIntervalsEntity.setDeletedAt(resultSet.getString("deleted_at"));
                    disableEtaIntervalsEntity.setSource(resultSet.getString("source"));
                    intervalResult.add(disableEtaIntervalsEntity);
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLEtaManager. Error: " + e.getMessage());
        }
        return intervalResult;
    }

    public List<DisableEtaIntervalsEntity> getDeletedIntervals(String storeUuid) {
        final var intervalResult = new ArrayList<DisableEtaIntervalsEntity>();

        try (final var connect = ConnectionManager.getDataSource(Db.PG_ETA).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE store_uuid = ?::uuid AND deleted_at IS NOT NULL ORDER BY id DESC ")) {
            preparedStatement.setString(1, storeUuid);
            try (final var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    var disableEtaIntervalsEntity = new DisableEtaIntervalsEntity();
                    disableEtaIntervalsEntity.setId(resultSet.getInt("id"));
                    disableEtaIntervalsEntity.setStoreUuid(resultSet.getString("store_uuid"));
                    disableEtaIntervalsEntity.setIntervalType(resultSet.getString("interval_type"));
                    disableEtaIntervalsEntity.setStartAt(resultSet.getString("start_at"));
                    disableEtaIntervalsEntity.setEndAt(resultSet.getString("end_at"));
                    disableEtaIntervalsEntity.setCreatedAt(resultSet.getString("created_at"));
                    disableEtaIntervalsEntity.setDeletedAt(resultSet.getString("deleted_at"));
                    disableEtaIntervalsEntity.setSource(resultSet.getString("source"));
                    intervalResult.add(disableEtaIntervalsEntity);
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLEtaManager. Error: " + e.getMessage());
        }
        return intervalResult;
    }

    public boolean addDisableIntervals (String storeId, String startAt, String endAt, String intervalType, String source) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ETA).getConnection();
             final var preparedStatement = connect.prepareStatement(INSERT_SQL + " (store_uuid, start_at, end_at, interval_type, source) " +
                     " VALUES (?::uuid, ?::timestamp, ?::timestamp, ?::disable_interval_type, ?::disable_interval_source) ")) {
            preparedStatement.setString(1, storeId);
            preparedStatement.setString(2, startAt);
            preparedStatement.setString(3, endAt);
            preparedStatement.setString(4, getZonedUTCDate());
            preparedStatement.setString(5, intervalType);
            preparedStatement.setString(6, source);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLEtaManager. Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public DisableEtaIntervalsEntity save(DisableEtaIntervalsEntity ticket) {
        return null;
    }

    @Override
    public void update(DisableEtaIntervalsEntity ticket) {

    }

    @Override
    public Optional<DisableEtaIntervalsEntity> findById(Integer id) {
        DisableEtaIntervalsEntity intervals = null;
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ETA).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE id = ? ")) {
            preparedStatement.setInt(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    intervals = new DisableEtaIntervalsEntity();
                    intervals.setId(resultSet.getInt("id"));
                    intervals.setStoreUuid(resultSet.getString("store_uuid"));
                    intervals.setIntervalType(resultSet.getString("interval_type"));
                    intervals.setStartAt(resultSet.getString("start_at"));
                    intervals.setEndAt(resultSet.getString("end_at"));
                    intervals.setCreatedAt(resultSet.getString("created_at"));
                    intervals.setDeletedAt(resultSet.getString("deleted_at"));
                    intervals.setSource(resultSet.getString("source"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLEtaManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(intervals);
    }

    @Override
    public List<DisableEtaIntervalsEntity> findAll() {
        return null;
    }
}
