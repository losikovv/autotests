package ru.instamart.jdbc.dao.eta;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.eta.StoreParametersEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public final class StoreParametersDao extends AbstractDao<String, StoreParametersEntity> {

    public static final StoreParametersDao INSTANCE = new StoreParametersDao();
    private final String SELECT_SQL = "SELECT %s FROM store_parameters ";
    private final String UPDATE_SQL = "UPDATE store_parameters SET ";
    private final String INSERT_SQL = "INSERT INTO store_parameters ";
    private final String DELETE_SQL = "DELETE FROM store_parameters ";

    public boolean updateStoreWorkingTime(String storeUuid, String openingTime, String closingTime, String closingDelta) {
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_ETA).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_SQL + " store_opening_time = ?::timetz, store_closing_time = ?::timetz, on_demand_closing_delta = ?::interval " +
                     "WHERE id = ?::uuid")) {
            preparedStatement.setString(1, openingTime);
            preparedStatement.setString(2, closingTime);
            preparedStatement.setString(3, closingDelta);
            preparedStatement.setString(4, storeUuid);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLEtaManager. Error: " + e.getMessage());
        }
        return false;
    }

    public boolean updateStoreMLStatus(String storeUuid, Boolean isMlEnabled) {
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_ETA).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_SQL + " is_ml_enabled = ? " +
                     "WHERE id = ?::uuid")) {
            preparedStatement.setBoolean(1, isMlEnabled);
            preparedStatement.setString(2, storeUuid);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLEtaManager. Error: " + e.getMessage());
        }
        return false;
    }

    public boolean addStore(String storeId, Float lat, Float lon, String timezone, Boolean isMlEnabled, String openingTime, String closingTime, String closingDelta, Boolean isSigmaEnabled) {
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_ETA).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(INSERT_SQL + " (id, lat, lon, timezone, is_ml_enabled, store_opening_time, store_closing_time, on_demand_closing_delta, is_sigma_enabled, retailer_id, avg_positions_per_place, to_place_sec, collection_speed_sec_per_pos) " +
                     "VALUES (?::uuid, ?, ?, ?, ?, ?::timetz, ?::timetz, ?::interval, ?, ?, ?, ?::interval, ?::interval) ")) {
            preparedStatement.setString(1, storeId);
            preparedStatement.setFloat(2, lat);
            preparedStatement.setFloat(3, lon);
            preparedStatement.setString(4, timezone);
            preparedStatement.setBoolean(5, isMlEnabled);
            preparedStatement.setString(6, openingTime);
            preparedStatement.setString(7, closingTime);
            preparedStatement.setString(8, closingDelta);
            preparedStatement.setBoolean(9, isSigmaEnabled);
            preparedStatement.setInt(10, 1);
            preparedStatement.setInt(11, 7);
            preparedStatement.setString(12, "00:07:00");
            preparedStatement.setString(13, "00:01:00");
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLEtaManager. Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(String storeId) {
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_ETA).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE id = ?::uuid ")) {
            preparedStatement.setString(1, storeId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Optional<StoreParametersEntity> findById(String id) {
        StoreParametersEntity storeParameters = null;
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ETA).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE id = ?::uuid")) {
            preparedStatement.setString(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    storeParameters = new StoreParametersEntity();
                    storeParameters.setId(resultSet.getString("id"));
                    storeParameters.setRetailerId(resultSet.getLong("retailer_id"));
                    storeParameters.setLat(resultSet.getFloat("lat"));
                    storeParameters.setLon(resultSet.getFloat("lon"));
                    storeParameters.setIsMlEnabled(resultSet.getBoolean("is_ml_enabled"));
                    storeParameters.setAvgPositionsPerPlace(resultSet.getInt("avg_positions_per_place"));
                    storeParameters.setToPlaceSec(resultSet.getString("to_place_sec"));
                    storeParameters.setCollectionSpeedSecPerPos(resultSet.getString("collection_speed_sec_per_pos"));
                    storeParameters.setStoreOpeningTime(resultSet.getString("store_opening_time"));
                    storeParameters.setStoreClosingTime(resultSet.getString("store_closing_time"));
                    storeParameters.setOnDemandClosingDelta(resultSet.getString("on_demand_closing_delta"));
                    storeParameters.setTimezone(resultSet.getString("timezone"));
                    storeParameters.setIsSigmaEnabled(resultSet.getBoolean("is_sigma_enabled"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLEtaManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(storeParameters);
    }
}
