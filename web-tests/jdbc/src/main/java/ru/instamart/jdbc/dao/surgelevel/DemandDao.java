package ru.instamart.jdbc.dao.surgelevel;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.shippingcalc.StrategiesEntity;
import ru.instamart.jdbc.entity.surgelevel.DemandEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class DemandDao extends AbstractDao<String, DemandEntity> {

    public static final DemandDao INSTANCE = new DemandDao();

    public DemandEntity findDemand(String storeId, String shipmentId) {
        final var demand = new DemandEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SURGE_LEVEL).getConnection();
             final var preparedStatement = connect.prepareStatement(" SELECT * FROM demand WHERE store_id = ?::uuid AND shipment_id = ?::uuid ")) {
            preparedStatement.setString(1, storeId);
            preparedStatement.setString(2, shipmentId);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    demand.setStoreId(resultSet.getString("store_id"));
                    demand.setShipmentId(resultSet.getString("shipment_id"));
                    demand.setCreatedAt(resultSet.getString("created_at"));
                    demand.setDistance(resultSet.getDouble("distance"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return demand;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Optional<DemandEntity> findById(String id) {
        return Optional.empty();
    }
}
