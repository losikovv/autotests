package ru.instamart.jdbc.dao.orders_service;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.order_service.PlacesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class PlacesDao implements Dao<String, PlacesDao> {
    public static final PlacesDao INSTANCE = new PlacesDao();
    private final String SELECT_SQL = "SELECT %s FROM places";

    private final String UPDATE_SQL = "UPDATE places";

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public PlacesDao save(PlacesDao ticket) {
        return null;
    }

    @Override
    public void update(PlacesDao ticket) {

    }

    @Override
    public Optional<PlacesDao> findById(String id) {
        return Optional.empty();
    }

    public PlacesEntity findByPlaceUuid(String placeUUID) {
        PlacesEntity placesEntity = null;
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ORDER).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE uuid = ?::uuid LIMIT 1")) {
            preparedStatement.setString(1, placeUUID);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    placesEntity = new PlacesEntity();
                    placesEntity.setUuid(resultSet.getString("uuid"));
                    placesEntity.setDeliveryZoneId(resultSet.getInt("delivery_zone_id"));
                    placesEntity.setPlaceLocation(resultSet.getString("place_location"));
                    placesEntity.setAssemblyTaskType(resultSet.getString("assembly_task_type"));
                    placesEntity.setDeliveryTaskType(resultSet.getString("delivery_task_type"));
                    placesEntity.setPlaceAvailableTasksToBeAssigned(resultSet.getString("place_available_tasks_to_be_assigned"));
                    placesEntity.setAveragePerPositionAssemblyTime(resultSet.getInt("average_per_position_assembly_time"));
                    placesEntity.setCreatedAt(resultSet.getString("created_at"));
                    placesEntity.setUpdatedAt(resultSet.getString("updated_at"));
                    placesEntity.setAdditionalSecondsForAssembly(resultSet.getInt("additional_seconds_for_assembly"));
                    placesEntity.setRetailerUuid(resultSet.getString("retailer_uuid"));
                    placesEntity.setSlaMin(resultSet.getInt("sla_min"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLOrderServiceManager. Error: " + e.getMessage());
        }
        return placesEntity;
    }

    @Override
    public List<PlacesDao> findAll() {
        return null;
    }

    public void updatePlaces(String retailerUuid, String assemblyTaskType, String uuid, Integer sla) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ORDER).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(UPDATE_SQL) + " SET retailer_uuid = ?::uuid, assembly_task_type = ?::assembly_task_type, sla_min = ?::integer WHERE uuid = ?::uuid")) {
            preparedStatement.setString(1, retailerUuid);
            preparedStatement.setString(2, assemblyTaskType);
            preparedStatement.setInt(3, sla);
            preparedStatement.setString(4, uuid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
