package ru.instamart.jdbc.dao.surgelevel;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.surgelevel.DemandEntity;
import ru.instamart.jdbc.entity.surgelevel.SupplyEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SupplyDao extends AbstractDao<String, SupplyEntity> {

    public static final SupplyDao INSTANCE = new SupplyDao();

    public SupplyEntity findSupply(String storeId, String candidateId) {
        final var supply = new SupplyEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SURGE_LEVEL).getConnection();
             final var preparedStatement = connect.prepareStatement(" SELECT * FROM supply WHERE store_id = ?::uuid AND candidate_id = ?::uuid ")) {
            preparedStatement.setString(1, storeId);
            preparedStatement.setString(2, candidateId);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    supply.setStoreId(resultSet.getString("store_id"));
                    supply.setCandidateId(resultSet.getString("candidate_id"));
                    supply.setCreatedAt(resultSet.getString("created_at"));
                    supply.setDistance(resultSet.getDouble("distance"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return supply;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Optional<SupplyEntity> findById(String id) {
        return Optional.empty();
    }
}
