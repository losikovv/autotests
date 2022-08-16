package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.stf.StoresEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class StoresDao implements Dao<Integer, StoresEntity> {

    public static final StoresDao INSTANCE = new StoresDao();
    private final String SELECT_SQL = "SELECT * FROM stores ";
    private final String SELECT_COUNT_SQL = "SELECT COUNT(*) AS total FROM stores ";
    private final String SELECT_JOIN_STORE_LOCATIONS = "SELECT s.* FROM stores s JOIN store_locations sl ON s.id = sl.store_id ";
    private final String SELECT_JOIN_STORE_SHIPPING_METHODS = "SELECT COUNT(DISTINCT s.city_id) AS total FROM stores s " +
            "JOIN store_shipping_methods ssm ON s.id = ssm.store_id JOIN spree_shipping_methods sm ON sm.id = ssm.shipping_method_id";
    private final String DELETE_SQL = "DELETE FROM stores, store_locations USING stores, store_locations ";
    private final String UPDATE_SQL = "UPDATE stores SET ";

    @Override
    public boolean delete(Integer id) {
        int result = 0;
        try (Connection connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL +
                     "WHERE stores.id  = store_locations.store_id AND stores.id = ?")) {
            preparedStatement.setLong(1, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
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

    public boolean updateWithSetAvailability(Integer storeId, final String availabilityDate) {
        int result = 0;
        try (Connection connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_SQL + "available_on = ? WHERE id = ?")) {
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
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(SELECT_COUNT_SQL);
             final var resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                resultCount = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return resultCount;
    }

    public StoresEntity getStoreByCoordinates(Double lat, Double lon) {
        final var store = new StoresEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(SELECT_JOIN_STORE_LOCATIONS +
                     " WHERE sl.lat = ? AND sl.lon = ? ORDER by sl.id DESC LIMIT 1")) {
            preparedStatement.setDouble(1, lat);
            preparedStatement.setDouble(2, lon);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    store.setId(resultSet.getInt("id"));
                    store.setRetailerId(resultSet.getLong("retailer_id"));
                    store.setTimeZone(resultSet.getString("time_zone"));
                    store.setOperationalZoneId(resultSet.getLong("operational_zone_id"));
                    store.setUuid(resultSet.getString("uuid"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return store;
    }

    public int getUniqueCitiesCountByShippingMethod(String shippingMethod) {
        int resultCount = 0;
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(SELECT_JOIN_STORE_SHIPPING_METHODS +
                     " WHERE sm.kind = ? AND s.available_on IS NOT NULL")) {
            preparedStatement.setString(1, shippingMethod);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    resultCount = resultSet.getInt("total");
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return resultCount;
    }

    @Override
    public Optional<StoresEntity> findById(Integer id) {
        StoresEntity store = new StoresEntity();
        var sql = SELECT_SQL + " WHERE id = ?";
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    store.setId(resultSet.getInt("id"));
                    store.setRetailerId(resultSet.getLong("retailer_id"));
                    store.setTimeZone(resultSet.getString("time_zone"));
                    store.setOperationalZoneId(resultSet.getLong("operational_zone_id"));
                    store.setUuid(resultSet.getString("uuid"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.of(store);
    }

    public StoresEntity getUnavailableStore() {
        final var store = new StoresEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(SELECT_SQL + "  WHERE available_on IS NULL ORDER BY rand() LIMIT 1");
             final var resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                store.setId(resultSet.getInt("id"));
                store.setRetailerId(resultSet.getLong("retailer_id"));
                store.setTimeZone(resultSet.getString("time_zone"));
                store.setOperationalZoneId(resultSet.getLong("operational_zone_id"));
                store.setUuid(resultSet.getString("uuid"));
                store.setCityId(resultSet.getLong("city_id"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return store;
    }

    public StoresEntity getStoreWithTimezone(String timeZone) {
        final var store = new StoresEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(SELECT_SQL + " WHERE time_zone = ? ORDER BY rand() LIMIT 1")) {
            preparedStatement.setString(1, timeZone);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    store.setId(resultSet.getInt("id"));
                    store.setRetailerId(resultSet.getLong("retailer_id"));
                    store.setTimeZone(resultSet.getString("time_zone"));
                    store.setOperationalZoneId(resultSet.getLong("operational_zone_id"));
                    store.setUuid(resultSet.getString("uuid"));
                    store.setCityId(resultSet.getLong("city_id"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return store;
    }

    public void updateOnDemandStore(int storeId, String openingTime, String closingTime, int closingDelta) {
        try (Connection connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_SQL + " on_demand = 1, opening_time = ?, closing_time = ?, on_demand_closing_delta = ? WHERE id = ?")) {
            preparedStatement.setString(1, openingTime);
            preparedStatement.setString(2, closingTime);
            preparedStatement.setInt(3, closingDelta);
            preparedStatement.setInt(4, storeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }

    @Override
    public List<StoresEntity> findAll() {
        return null;
    }
}
