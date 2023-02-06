package ru.instamart.jdbc.dao.shifts;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.shifts.ShopsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.fail;

public class ShopsDao extends AbstractDao<Long, ShopsEntity> {

    public static final ShopsDao INSTANCE = new ShopsDao();
    private final String SELECT_ORIGINAL_ID_DESK = "SELECT uuid, original_id " +
            "FROM public.shops " +
            "WHERE EXISTS(SELECT 1 FROM planning_areas WHERE shops.delivery_area_id = planning_areas.delivery_area_id)\n" +
            "ORDER BY original_id DESC LIMIT 1";
    private final String DELETE = "DELETE FROM public.shops";

    public List<ShopsEntity> getOriginalId() {
        final var shopsResult = new ArrayList<ShopsEntity>();
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIFT).getConnection();
             final var preparedStatement = connect.prepareStatement(SELECT_ORIGINAL_ID_DESK);
             final var resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                var shopsEntity = new ShopsEntity();
                shopsEntity.setUuid(resultSet.getString("uuid"));
                shopsEntity.setOriginalId(resultSet.getInt("original_id"));
                shopsResult.add(shopsEntity);
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShiftsManager. Error: " + e.getMessage());
        }
        return shopsResult;
    }

    public List<ShopsEntity> getOriginalId(final Integer originalId) {
        final var shopsResult = new ArrayList<ShopsEntity>();
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIFT).getConnection();
             final var preparedStatement = connect.prepareStatement("SELECT * FROM public.shops WHERE original_id=?")) {
            preparedStatement.setInt(1, originalId);
            try (final var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    var shopsEntity = new ShopsEntity();
                    shopsEntity.setId(resultSet.getInt("id"));
                    shopsEntity.setCreatedAt(resultSet.getString("created_at"));
                    shopsEntity.setUpdatedAt(resultSet.getString("updated_at"));
                    shopsEntity.setDeletedAt(resultSet.getString("deleted_at"));
                    shopsEntity.setName(resultSet.getString("name"));
                    shopsEntity.setRegionId(resultSet.getInt("region_id"));
                    shopsEntity.setDeliveryAreaId(resultSet.getInt("delivery_area_id"));
                    shopsEntity.setPoint(resultSet.getString("point"));
                    shopsEntity.setUuid(resultSet.getString("uuid"));
                    shopsEntity.setOriginalId(resultSet.getInt("original_id"));
                    shopsEntity.setBaseStoreId(resultSet.getInt("base_store_id"));
                    shopsEntity.setAvailableOn(resultSet.getString("available_on"));
                    shopsEntity.setExpressDelivery(resultSet.getBoolean("express_delivery"));
                    shopsEntity.setOpeningTime(resultSet.getString("opening_time"));
                    shopsEntity.setClosingTime(resultSet.getString("closing_time"));
                    shopsEntity.setScheduleType(resultSet.getString("schedule_type"));
                    shopsResult.add(shopsEntity);
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShiftsManager. Error: " + e.getMessage());
        }
        return shopsResult;
    }

    public boolean delete(Integer baseStoreId) {
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_SHIFT).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE + " WHERE delivery_area_id = ?")) {
            preparedStatement.setInt(1, baseStoreId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return false;
    }
}
