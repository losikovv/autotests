package ru.instamart.jdbc.dao.eta;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.eta.StoreParametersEntity;
import ru.instamart.jdbc.util.dispatch.ConnectionPgSQLEtaManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class StoreParametersDao implements Dao<String, StoreParametersEntity> {

    public static final StoreParametersDao INSTANCE = new StoreParametersDao();
    private final String UPDATE_SQL = "UPDATE store_parameters SET";
    private final String SELECT_SQL = "SELECT %s FROM store_parameters";

    public void updateStoreParameters(String storeUuid, String openingTime, String closingTime, String closingDelta) {
        try (Connection connect = ConnectionPgSQLEtaManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_SQL + " store_opening_time = ?::timetz, store_closing_time = ?::timetz, on_demand_closing_delta = ?::interval " +
                     "WHERE id = ?::uuid")) {
            preparedStatement.setString(1, openingTime);
            preparedStatement.setString(2, closingTime);
            preparedStatement.setString(3, closingDelta);
            preparedStatement.setString(4, storeUuid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLEtaManager. Error: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public StoreParametersEntity save(StoreParametersEntity ticket) {
        return null;
    }

    @Override
    public void update(StoreParametersEntity ticket) {

    }

    @Override
    public Optional<StoreParametersEntity> findById(String id) {
        StoreParametersEntity storeParameters = null;
        try (Connection connect = ConnectionPgSQLEtaManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE id = ?::uuid")) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                storeParameters = new  StoreParametersEntity();
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
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLEtaManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(storeParameters);
    }

    @Override
    public List<StoreParametersEntity> findAll() {
        return null;
    }
}
