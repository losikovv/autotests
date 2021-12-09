package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.StoresEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StoresDao implements Dao<Long, StoresEntity> {

    public static final StoresDao INSTANCE = new StoresDao();
    private final String SELECT_SQL = "SELECT %s FROM stores";

    @Override
    public boolean delete(Long id) {
        int result = 0;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement("DELETE FROM stores, store_locations USING stores, store_locations" +
                     " WHERE stores.id  = store_locations.store_id AND stores.id = ?")) {
            preparedStatement.setLong(1, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result == 2;
    }

    @Override
    public StoresEntity save(StoresEntity ticket) {
        return null;
    }

    @Override
    public void update(StoresEntity ticket) {

    }

    @Override
    public Optional<StoresEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<StoresEntity> findAll() {
        return null;
    }

    public int getCount() {
        int resultCount = 0;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total"))) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            resultCount = resultSet.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultCount;
    }

    public StoresEntity getStoreByCoordinates(Double lat, Double lon) {
        StoresEntity store = new StoresEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "s.*") + " s JOIN store_locations sl ON s.id = sl.store_id" +
                     " WHERE sl.lat = ? AND sl.lon = ?")) {
            preparedStatement.setDouble(1, lat);
            preparedStatement.setDouble(2, lon);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                store.setId(resultSet.getLong("id"));
                store.setRetailerId(resultSet.getLong("retailer_id"));
                store.setTimeZone(resultSet.getString("time_zone"));
                store.setOperationalZoneId(resultSet.getLong("operational_zone_id"));
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return store;
    }
}
