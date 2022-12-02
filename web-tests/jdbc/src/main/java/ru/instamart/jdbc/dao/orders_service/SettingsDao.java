package ru.instamart.jdbc.dao.orders_service;

import lombok.Data;
import ru.instamart.jdbc.dao.Dao;

import ru.instamart.jdbc.entity.order_service.RetailersEntity;
import ru.instamart.jdbc.entity.order_service.SettingsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SettingsDao implements Dao<String, SettingsEntity> {

    public static final SettingsDao INSTANCE = new SettingsDao();

    private final String SELECT_SQL = "SELECT %s FROM settings";
    private final String UPDATE_SQL = "UPDATE settings";

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public SettingsEntity save(SettingsEntity ticket) {
        return null;
    }

    @Override
    public void update(SettingsEntity ticket) {

    }

    @Override
    public Optional<SettingsEntity> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<SettingsEntity> findAll() {
        return null;
    }

    public SettingsEntity findByPlaceUUID(String placeUUID) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ORDER).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE place_uuid = ?::uuid LIMIT 1")) {
            preparedStatement.setString(1, placeUUID);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    SettingsEntity settingsEntity;
                    settingsEntity = new SettingsEntity();
                    settingsEntity.setPlaceUuid(resultSet.getString("place_uuid"));
                    settingsEntity.setMaxOrderAssignRetryCount(resultSet.getInt("max_order_assign_retry_count"));
                    settingsEntity.setAvgParkingMinVehicle(resultSet.getInt("avg_parking_min_vehicle"));
                    settingsEntity.setMaxCurrentOrderAssignQueue(resultSet.getInt("max_current_order_assign_queue"));
                    settingsEntity.setOrderWeightThresholdToAssignToVehicleGramms(resultSet.getInt("order_weight_threshold_to_assign_to_vehicle_gramms"));
                    settingsEntity.setAverageSpeedForStraightDistanceToClientMin(resultSet.getInt("average_speed_for_straight_distance_to_client_min"));
                    settingsEntity.setAdditionalFactorForStraightDistanceToClientMin(resultSet.getInt("additional_factor_for_straight_distance_to_client_min"));
                    settingsEntity.setOrderTransferTimeFromAssemblyToDeliveryMin(resultSet.getInt("order_transfer_time_from_assembly_to_delivery_min"));
                    settingsEntity.setAvgToPlaceMinExternal(resultSet.getInt("avg_to_place_min_external"));
                    settingsEntity.setAvgToPlaceMin(resultSet.getInt("avg_to_place_min"));
                    settingsEntity.setOfferSeenTimeoutSec(resultSet.getInt("offer_seen_timeout_sec"));
                    settingsEntity.setPlaceLocationCenter(resultSet.getBoolean("place_location_center"));
                    settingsEntity.setLastPositionExpire(resultSet.getInt("last_position_expire"));
                    settingsEntity.setTaxiDeliveryOnly(resultSet.getBoolean("taxi_delivery_only"));
                    settingsEntity.setOrderTransferTimeFromDeliveryToClientMin(resultSet.getInt("order_transfer_time_from_delivery_to_client_min"));
                    settingsEntity.setOrderReceiveTimeFromAssemblyToDeliveryMin(resultSet.getInt("order_receive_time_from_assembly_to_delivery_min"));
                    settingsEntity.setOfferServerTimeoutSec(resultSet.getInt("offer_server_timeout_sec"));
                    settingsEntity.setExternalAssembliersPresented(resultSet.getBoolean("external_assembliers_presented"));
                    settingsEntity.setGapTaxiPunishMin(resultSet.getInt("gap_taxi_punish_min"));
                    settingsEntity.setTaxiAvailable(resultSet.getBoolean("taxi_available"));
                    settingsEntity.setMaxWaitingTimeForCourierMin(resultSet.getInt("max_waiting_time_for_courier_min"));
                    settingsEntity.setCreatedAt(resultSet.getString("created_at"));
                    settingsEntity.setUpdatedAt(resultSet.getString("updated_at"));
                    settingsEntity.setRteFactorCityCongestionMinutes(resultSet.getInt("rte_factor_city_congestion_minutes"));
                    settingsEntity.setRteDeliverySlotMultiplier(resultSet.getFloat("rte_delivery_slot_multiplier"));
                    return settingsEntity;
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLOrderServiceManager. Error: " + e.getMessage());
        }
        return null;
    }


    public void updateSettings(String placeUUID, int factorCityCongestionMinute, double deliverySlotMultiplier) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ORDER).getConnection();
             final var preparedStatement = connect.prepareStatement(UPDATE_SQL + " SET rte_factor_city_congestion_minutes = ?::int, rte_delivery_slot_multiplier = ?::float WHERE place_uuid = ?::uuid")) {
            preparedStatement.setInt(1, factorCityCongestionMinute);
            preparedStatement.setDouble(2, deliverySlotMultiplier);
            preparedStatement.setString(3, placeUUID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }



}
