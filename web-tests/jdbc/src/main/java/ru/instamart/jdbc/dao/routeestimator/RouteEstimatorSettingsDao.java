package ru.instamart.jdbc.dao.routeestimator;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.routeestimator.RouteEstimatorSettingsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class RouteEstimatorSettingsDao implements Dao<String, RouteEstimatorSettingsEntity> {
    public static final RouteEstimatorSettingsDao INSTANCE = new RouteEstimatorSettingsDao();
    private final String SELECT_SQL = "SELECT %s FROM route_estimator_settings";

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public RouteEstimatorSettingsEntity save(RouteEstimatorSettingsEntity ticket) {
        return null;
    }

    @Override
    public void update(RouteEstimatorSettingsEntity ticket) {

    }

    @Override
    public Optional<RouteEstimatorSettingsEntity> findById(String id) {
        return Optional.empty();
    }

    public RouteEstimatorSettingsEntity findByStoreUuid(String storeUuid) {
        RouteEstimatorSettingsEntity routeEstimatorSettingsEntity = null;
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ROUTE_ESTIMATOR).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE place_uuid = ?::uuid LIMIT 1")) {
            preparedStatement.setString(1, storeUuid);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    routeEstimatorSettingsEntity = new RouteEstimatorSettingsEntity();
                    routeEstimatorSettingsEntity.setPlace_uuid(resultSet.getString("place_uuid"));
                    routeEstimatorSettingsEntity.setAvgParkingMinVehicle(resultSet.getInt("avg_parking_min_vehicle"));
                    routeEstimatorSettingsEntity.setAverageSpeedForStraightDistanceToClientMin(resultSet.getInt("average_speed_for_straight_distance_to_client_min"));
                    routeEstimatorSettingsEntity.setAdditionalFactorForStraightDistanceToClientMin(resultSet.getInt("additional_factor_for_straight_distance_to_client_min"));
                    routeEstimatorSettingsEntity.setOrderTransferTimeFromAssemblyToDeliveryMin(resultSet.getInt("order_transfer_time_from_assembly_to_delivery_min"));
                    routeEstimatorSettingsEntity.setAvgToPlaceMinExternal(resultSet.getInt("avg_to_place_min_external"));
                    routeEstimatorSettingsEntity.setOrderTransferTimeFromDeliveryToClientMin(resultSet.getInt("order_transfer_time_from_delivery_to_client_min"));
                    routeEstimatorSettingsEntity.setOrderReceiveTimeFromAssemblyToDeliveryMin(resultSet.getInt("order_receive_time_from_assembly_to_delivery_min"));
                    routeEstimatorSettingsEntity.setAveragePerPositionAssemblyTime(resultSet.getInt("average_per_position_assembly_time"));
                    routeEstimatorSettingsEntity.setPlaceLocation(resultSet.getString("place_location"));
                    routeEstimatorSettingsEntity.setCreatedAt(resultSet.getString("created_at"));
                    routeEstimatorSettingsEntity.setUpdatedAt(resultSet.getString("updated_at"));
                    routeEstimatorSettingsEntity.setAdditionalAssemblyTime(resultSet.getInt("additional_assembly_time"));
                    routeEstimatorSettingsEntity.setOperationalZoneId(resultSet.getInt("operational_zone_id"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLOrderServiceManager. Error: " + e.getMessage());
        }
        return routeEstimatorSettingsEntity;
    }

    @Override
    public List<RouteEstimatorSettingsEntity> findAll() {
        return null;
    }
}
