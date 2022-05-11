package ru.instamart.jdbc.dao.eta;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.eta.StoreParametersEntity;
import ru.instamart.jdbc.util.dispatch.ConnectionPgSQLEtaManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class StoreParametersDao implements Dao<String, StoreParametersEntity> {

    public static final StoreParametersDao INSTANCE = new StoreParametersDao();
    private final String UPDATE_SQL = "UPDATE store_parameters SET";

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
        return Optional.empty();
    }

    @Override
    public List<StoreParametersEntity> findAll() {
        return null;
    }
}
