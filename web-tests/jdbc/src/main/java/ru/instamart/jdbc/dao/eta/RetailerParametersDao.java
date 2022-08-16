package ru.instamart.jdbc.dao.eta;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.eta.RetailerParametersEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class RetailerParametersDao extends AbstractDao<Long, RetailerParametersEntity> {

    public static final RetailerParametersDao INSTANCE = new RetailerParametersDao();
    private final String SELECT_SQL = "SELECT %s FROM retailer_parameters";

    @Override
    public Optional<RetailerParametersEntity> findById(Long id) {
        RetailerParametersEntity retailerParameters = null;
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ETA).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    retailerParameters = new RetailerParametersEntity();
                    retailerParameters.setId(resultSet.getLong("id"));
                    retailerParameters.setCourierSpeed(resultSet.getInt("courier_speed"));
                    retailerParameters.setDeliveryTimeSigma(resultSet.getString("delivery_time_sigma"));
                    retailerParameters.setWindow(resultSet.getString("window"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLEtaManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(retailerParameters);
    }
}
