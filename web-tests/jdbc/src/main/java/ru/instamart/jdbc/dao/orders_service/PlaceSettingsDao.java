package ru.instamart.jdbc.dao.orders_service;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.order_service.PlaceSettingsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;


public class PlaceSettingsDao implements Dao<String,PlaceSettingsEntity> {
    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public PlaceSettingsEntity save(PlaceSettingsEntity ticket) {
        return null;
    }

    @Override
    public void update(PlaceSettingsEntity ticket) {

    }

    @Override
    public Optional<PlaceSettingsEntity> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<PlaceSettingsEntity> findAll() {
        return null;
    }

    public static final PlaceSettingsDao INSTANCE = new PlaceSettingsDao();
    private final String SELECT_SQL = "SELECT %s FROM place_settings";

    public PlaceSettingsEntity getScheduleType(String placeUUID) {
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_ORDER).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE place_uuid = ?::uuid LIMIT 1")) {
            preparedStatement.setString(1, placeUUID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var placeSettingsEntity = new PlaceSettingsEntity();
                placeSettingsEntity.setPlaceUuid(resultSet.getString("place_uuid"));
                placeSettingsEntity.setMaxOrderAssingRetryCount(resultSet.getInt("max_order_assign_retry_count"));
                placeSettingsEntity.setCreatedAt(resultSet.getString("created_at"));
                placeSettingsEntity.setUpdatedAt(resultSet.getString("updated_at"));
                placeSettingsEntity.setScheduleType(resultSet.getString("schedule_type"));
                placeSettingsEntity.setAutoRouting(resultSet.getBoolean("auto_routing"));
                placeSettingsEntity.setPeriodForTimeToThrowMin(resultSet.getInt("period_for_time_to_throw_min"));
                placeSettingsEntity.setOperationalZoneId(resultSet.getInt("operational_zone_id"));
                return placeSettingsEntity;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLOrderServiceManager. Error: " + e.getMessage());
        }
        return null;
    }

}
