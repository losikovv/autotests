package ru.instamart.jdbc.dao.shifts;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.shifts.PlanningAreasEntity;
import ru.instamart.jdbc.entity.shifts.PlanningPeriodsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.fail;

public class PlanningAreasDao extends AbstractDao<Long, PlanningPeriodsEntity> {

    public static final PlanningAreasDao INSTANCE = new PlanningAreasDao();
    private final String SELECT_SQL = "SELECT * FROM planning_areas WHERE delivery_area_id=?;";

    public List<PlanningAreasEntity> getPlanningAreas(Integer deliveryAreaId) {
        final var planningAreasResult = new ArrayList<PlanningAreasEntity>();
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIFT).getConnection();
             final var preparedStatement = connect.prepareStatement(SELECT_SQL)) {
            preparedStatement.setLong(1, deliveryAreaId);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    final var planningAreasEntity = new PlanningAreasEntity();
                    planningAreasEntity.setId(resultSet.getLong("id"));
                    planningAreasEntity.setCreatedAt(resultSet.getString("created_at"));
                    planningAreasEntity.setUpdatedAt(resultSet.getString("updated_at"));
                    planningAreasEntity.setDeletedAt(resultSet.getString("deleted_at"));
                    planningAreasEntity.setName(resultSet.getString("name"));
                    planningAreasEntity.setRegionId(resultSet.getLong("region_id"));
                    planningAreasEntity.setPoly(resultSet.getString("poly"));
                    planningAreasEntity.setExternalId(resultSet.getLong("external_id"));
                    planningAreasEntity.setStoreId(resultSet.getLong("store_id"));
                    planningAreasEntity.setDeliveryAreaId(resultSet.getLong("delivery_area_id"));
                    planningAreasEntity.setMaxFixPayrollPerHourByRole(resultSet.getString("max_fix_payroll_per_hour_by_role"));
                    planningAreasEntity.setSurgedByRole(resultSet.getString("surged_by_role"));
                    planningAreasEntity.setMaxShiftDuration(resultSet.getLong("max_shift_duration"));
                    planningAreasEntity.setMaxPeoplesCountEnabled(resultSet.getBoolean("max_peoples_count_enabled"));
                    planningAreasResult.add(planningAreasEntity);
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShiftsManager. Error: " + e.getMessage());
        }
        return planningAreasResult;
    }
}
