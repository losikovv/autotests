package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.StoresEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class StoresDao extends AbstractDao<Long, StoresEntity> {

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
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return result == 2;
    }

    public boolean updateWithSetAvailability(Long storeId, final String availabilityDate) {
        int result = 0;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement("UPDATE stores SET available_on = ? WHERE id = ?")) {
            preparedStatement.setString(1, availabilityDate);
            preparedStatement.setLong(2, storeId);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return result == 2;
    }

    public int getCount() {
        int resultCount = 0;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total"))) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            resultCount = resultSet.getInt("total");
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return resultCount;
    }

    public StoresEntity getStoreByCoordinates(Double lat, Double lon) {
        StoresEntity store = new StoresEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "s.*") + " s JOIN store_locations sl ON s.id = sl.store_id" +
                     " WHERE sl.lat = ? AND sl.lon = ? ORDER by sl.id DESC LIMIT 1")) {
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
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return store;
    }

    public int getUniqueCitiesCountByShippingMethod(String shippingMethod) {
        int resultCount = 0;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(DISTINCT s.city_id) AS total") +
                     " s JOIN store_shipping_methods ssm ON s.id = ssm.store_id JOIN spree_shipping_methods sm ON sm.id = ssm.shipping_method_id" +
                     " WHERE sm.kind = ? AND s.available_on IS NOT NULL")) {
            preparedStatement.setString(1, shippingMethod);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            resultCount = resultSet.getInt("total");
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return resultCount;
    }

    @Override
    public Optional<StoresEntity> findById(Long id) {
        StoresEntity store = new StoresEntity();
        var sql = String.format(SELECT_SQL, "*") + " WHERE id = ?";
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                store.setId(resultSet.getLong("id"));
                store.setRetailerId(resultSet.getLong("retailer_id"));
                store.setTimeZone(resultSet.getString("time_zone"));
                store.setOperationalZoneId(resultSet.getLong("operational_zone_id"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.of(store);
    }
}
