package ru.instamart.jdbc.dao.shifts;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.dto.shifts.PlanningPeriodFilters;
import ru.instamart.jdbc.entity.shifts.PlanningPeriodCustomEntity;
import ru.instamart.jdbc.entity.shifts.PlanningPeriodEntity;
import ru.instamart.jdbc.entity.shifts.PlanningPeriodsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.stream.Collectors.joining;
import static org.testng.Assert.fail;
import static ru.instamart.jdbc.helper.JdbsHelpers.filtersMap;

public class PlanningPeriodsDao extends AbstractDao<Long, PlanningPeriodsEntity> {
    public static final PlanningPeriodsDao INSTANCE = new PlanningPeriodsDao();
    private final String SELECT_SQL = "SELECT * FROM planning_periods";
    private final String SELECT_JOIN_SQL = "SELECT pp.id AS planning_period_id, COUNT(s.id) AS people_count_capacity, pp.peoples_count AS people_count_available" +
            " FROM planning_periods pp " +
            "LEFT JOIN shifts s ON true " +
            "LEFT JOIN LATERAL jsonb_array_elements(s.planning_periods_ids) pps(json) ON pp.id = (pps.json->>'id')::INTEGER " +
            "WHERE (pps.json->>'guaranteed_payroll')::DECIMAL>0 AND pp.id IN (?) AND s.state NOT IN ('canceled', 'deleted') GROUP BY pp.id ORDER BY pp.id;";

    public Optional<PlanningPeriodCustomEntity> getClientTokenByName(final Long id) {
        PlanningPeriodCustomEntity planningPeriodCustomEntity = null;
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIFT).getConnection();
             final var preparedStatement = connect.prepareStatement(SELECT_JOIN_SQL)) {
            preparedStatement.setLong(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    planningPeriodCustomEntity = new PlanningPeriodCustomEntity();
                    planningPeriodCustomEntity.setPlanningPeriodId(resultSet.getLong("planning_period_id"));
                    planningPeriodCustomEntity.setPeopleCountCapacity(resultSet.getLong("people_count_capacity"));
                    planningPeriodCustomEntity.setPeopleCountAvailable(resultSet.getLong("people_count_available"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShiftsManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(planningPeriodCustomEntity);
    }

    public List<PlanningPeriodEntity> getPlanningPeriods(final PlanningPeriodFilters filters) {
        final var parameters = new CopyOnWriteArrayList<>();
        final var whereSql = new CopyOnWriteArrayList<String>();

        filtersMap(filters, parameters::add, whereSql::add);

        final var where = whereSql.stream()
                .collect(joining(" AND ", " WHERE ", " LIMIT 10"));
        final var sql = SELECT_SQL + where;

        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIFT).getConnection();
             final var preparedStatement = connect.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            try (final var resultSet = preparedStatement.executeQuery()) {
                final var result = new ArrayList<PlanningPeriodEntity>();
                if (resultSet.next()) {
                    var planningPeriodEntity = new PlanningPeriodEntity();
                    planningPeriodEntity.setId(resultSet.getInt("id"));
                    planningPeriodEntity.setCreatedAt(resultSet.getString("created_at"));
                    planningPeriodEntity.setUpdatedAt(resultSet.getString("updated_at"));
                    planningPeriodEntity.setDeletedAt(resultSet.getString("deleted_at"));
                    planningPeriodEntity.setPlanningAreaId(resultSet.getInt("planning_area_id"));
                    planningPeriodEntity.setStartedAt(resultSet.getString("started_at"));
                    planningPeriodEntity.setEndedAt(resultSet.getString("ended_at"));
                    planningPeriodEntity.setPeoplesCount(resultSet.getInt("peoples_count"));
                    planningPeriodEntity.setSurged(resultSet.getBoolean("surged"));
                    planningPeriodEntity.setMappedShops(resultSet.getString("mapped_shops"));
                    planningPeriodEntity.setImportId(resultSet.getString("import_id"));
                    planningPeriodEntity.setCreatorId(resultSet.getString("creator_id"));
                    planningPeriodEntity.setRole(resultSet.getString("role"));
                    planningPeriodEntity.setGuaranteedPayroll(resultSet.getString("guaranteed_payroll"));
                    planningPeriodEntity.setPredictedPayroll(resultSet.getString("predicted_payroll"));
                    planningPeriodEntity.setPublishedTimes(resultSet.getString("published_times"));
                    planningPeriodEntity.setPublishedTime(resultSet.getString("published_time"));
                    planningPeriodEntity.setPeoplesCountPredicted(resultSet.getInt("peoples_count_predicted"));
                    planningPeriodEntity.setMaxPeoplesCount(resultSet.getBoolean("max_peoples_count"));
                    result.add(planningPeriodEntity);
                }
                return result;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShiftsManager. Error: " + e.getMessage());
        }
        return null;
    }

}
