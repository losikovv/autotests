package ru.instamart.jdbc.dao.eta;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.eta.ServiceParametersEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class ServiceParametersDao extends AbstractDao<Long, ServiceParametersEntity> {

    public static final ServiceParametersDao INSTANCE = new ServiceParametersDao();
    private final String SELECT_SQL = "SELECT %s FROM service_parameters";
    private final String UPDATE_SQL = "UPDATE service_parameters SET";

    public ServiceParametersEntity getServiceParameters() {
        ServiceParametersEntity serviceParametersEntity = new ServiceParametersEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ETA).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*"));
             final var resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                serviceParametersEntity.setWaitMlTimeout(resultSet.getString("wait_ml_timeout"));
                serviceParametersEntity.setIsMlEnabled(resultSet.getBoolean("is_ml_enabled"));
                serviceParametersEntity.setAvgPositionsPerPlace(resultSet.getInt("avg_positions_per_place"));
                serviceParametersEntity.setToPlaceSec(resultSet.getString("to_place_sec"));
                serviceParametersEntity.setCollectionSpeedSecPerPos(resultSet.getString("collection_speed_sec_per_pos"));
                serviceParametersEntity.setStoreOpeningTime(resultSet.getString("store_opening_time"));
                serviceParametersEntity.setStoreClosingTime(resultSet.getString("store_closing_time"));
                serviceParametersEntity.setOnDemandClosingDelta(resultSet.getString("on_demand_closing_delta"));
                serviceParametersEntity.setCourierSpeed(resultSet.getInt("courier_speed"));
                serviceParametersEntity.setDeliveryTimeSigma(resultSet.getString("delivery_time_sigma"));
                serviceParametersEntity.setWindow(resultSet.getString("window"));
                serviceParametersEntity.setIsSigmaEnabled(resultSet.getBoolean("is_sigma_enabled"));
                serviceParametersEntity.setCourierSpeedDelivery(resultSet.getInt("courier_speed_delivery"));
                serviceParametersEntity.setCurveFactorDelivery(resultSet.getFloat("curve_factor_delivery"));
                serviceParametersEntity.setRouteEstimatorTimeout(resultSet.getString("route_estimator_timeout"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLEtaManager. Error: " + e.getMessage());
        }
        return serviceParametersEntity;
    }

    public void updateWaitMlTimeout(String waitMlTimeout) {
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_ETA).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_SQL + " wait_ml_timeout = ?::interval")) {
            preparedStatement.setString(1, waitMlTimeout);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLEtaManager. Error: " + e.getMessage());
        }
    }
}
