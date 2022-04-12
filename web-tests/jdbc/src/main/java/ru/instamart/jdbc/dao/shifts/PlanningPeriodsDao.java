package ru.instamart.jdbc.dao.shifts;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.shifts.PlanningPeriodEntity;
import ru.instamart.jdbc.entity.shifts.PlanningPeriodsEntity;
import ru.instamart.jdbc.util.ConnectionPgSQLManagerService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class PlanningPeriodsDao extends AbstractDao<Long, PlanningPeriodsEntity> {
    public static final PlanningPeriodsDao INSTANCE = new PlanningPeriodsDao();
    private final String SELECT_JOIN_SQL = "SELECT pp.id AS planning_period_id, COUNT(s.id) AS people_count_capacity, pp.peoples_count AS people_count_available" +
            " FROM planning_periods pp " +
            "LEFT JOIN shifts s ON true " +
            "LEFT JOIN LATERAL jsonb_array_elements(s.planning_periods_ids) pps(json) ON pp.id = (pps.json->>'id')::INTEGER " +
            "WHERE (pps.json->>'guaranteed_payroll')::DECIMAL>0 AND pp.id IN (?) AND s.state NOT IN ('canceled', 'deleted') GROUP BY pp.id ORDER BY pp.id;";

    public Optional<PlanningPeriodEntity> getClientTokenByName(Long id) {
        PlanningPeriodEntity planningPeriodEntity = null;
        try (Connection connect = ConnectionPgSQLManagerService.get();
             PreparedStatement preparedStatement = connect.prepareStatement(SELECT_JOIN_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                planningPeriodEntity = new PlanningPeriodEntity();
                planningPeriodEntity.setPlanningPeriodId(resultSet.getLong("planning_period_id"));
                planningPeriodEntity.setPeopleCountCapacity(resultSet.getLong("people_count_capacity"));
                planningPeriodEntity.setPeopleCountAvailable(resultSet.getLong("people_count_available"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(planningPeriodEntity);
    }
}
