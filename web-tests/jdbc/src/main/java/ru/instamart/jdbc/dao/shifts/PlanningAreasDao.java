package ru.instamart.jdbc.dao.shifts;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.shifts.PlanningAreasEntity;
import ru.instamart.jdbc.entity.shifts.PlanningPeriodsEntity;
import ru.instamart.jdbc.util.dispatch.ConnectionPgSQLShiftsManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.fail;

public class PlanningAreasDao extends AbstractDao<Long, PlanningPeriodsEntity> {

    public static final PlanningAreasDao INSTANCE = new PlanningAreasDao();
    private final String SELECT_SQL = "SELECT * FROM planning_areas WHERE delivery_area_id=?;";

    public List<PlanningAreasEntity> getPlanningAreas(Integer deliveryAreaId) {
        List<PlanningAreasEntity> planningAreasResult = new ArrayList<>();
        try (Connection connect = ConnectionPgSQLShiftsManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(SELECT_SQL)) {
            preparedStatement.setLong(1, deliveryAreaId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var planningAreasEntity = new PlanningAreasEntity();
                planningAreasEntity.setId(resultSet.getLong("id"));
                planningAreasEntity.setCreatedAt(resultSet.getString("created_at"));
                planningAreasEntity.setUpdatedAt(resultSet.getString("updated_at"));
                planningAreasEntity.setDeletedAt(resultSet.getString("deleted_at"));
                planningAreasEntity.setName(resultSet.getString("name"));
                planningAreasEntity.setRegionId(resultSet.getLong("region_id"));
                planningAreasEntity.setPoly(resultSet.getString("poly"));
                planningAreasEntity.setMaxFixPayrollPerHour(resultSet.getString("max_fix_payroll_per_hour"));
                planningAreasEntity.setSurged(resultSet.getBoolean("surged"));
                planningAreasEntity.setExternalId(resultSet.getLong("external_id"));
                planningAreasEntity.setStoreId(resultSet.getLong("store_id"));
                planningAreasEntity.setDeliveryAreaId(resultSet.getLong("delivery_area_id"));
                planningAreasEntity.setMaxFixPayrollPerHourByRole(resultSet.getString("max_fix_payroll_per_hour_by_role"));
                planningAreasEntity.setSurgedByRole(resultSet.getString("surged_by_role"));
                planningAreasEntity.setMaxShiftDuration(resultSet.getLong("max_shift_duration"));
                planningAreasResult.add(planningAreasEntity);
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShiftsManager. Error: " + e.getMessage());
        }
        return planningAreasResult;
    }
}
