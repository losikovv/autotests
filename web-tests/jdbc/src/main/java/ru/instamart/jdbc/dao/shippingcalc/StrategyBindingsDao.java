package ru.instamart.jdbc.dao.shippingcalc;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.shippingcalc.StrategyBindingsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class StrategyBindingsDao implements Dao<Integer, StrategyBindingsEntity> {
    public static final StrategyBindingsDao INSTANCE = new StrategyBindingsDao();
    private final String SELECT_SQL = "SELECT %s FROM strategy_bindings ";
    private final String INSERT_SQL = "INSERT INTO strategy_bindings ";

    public StrategyBindingsEntity getStrategyBinding(Integer id, String storeId, String tenantId, String shipping) {
        StrategyBindingsEntity strategyBinding = new StrategyBindingsEntity();

        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE strategy_id = ? AND store_id = ? AND tenant_id = ? AND shipping = ?::delivery_type ")) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, storeId);
            preparedStatement.setString(3, tenantId);
            preparedStatement.setString(4, shipping);

            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    strategyBinding = new StrategyBindingsEntity();
                    strategyBinding.setStrategyId(resultSet.getInt("strategy_id"));
                    strategyBinding.setStoreId(resultSet.getString("store_id"));
                    strategyBinding.setTenantId(resultSet.getString("tenant_id"));
                    strategyBinding.setShipping(resultSet.getString("shipping"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return strategyBinding;
    }

    public boolean addStrategyBinding(Integer strategyId, String storeId, String tenantId, String shipping) {
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(INSERT_SQL + " (strategy_id, store_id, tenant_id, shipping) " +
                     " VALUES (?, ?, ?, ?::delivery_type) ")) {
            preparedStatement.setInt(1, strategyId);
            preparedStatement.setString(2, storeId);
            preparedStatement.setString(3, tenantId);
            preparedStatement.setString(4, shipping);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public StrategyBindingsEntity save(StrategyBindingsEntity ticket) {
        return null;
    }

    @Override
    public void update(StrategyBindingsEntity ticket) {

    }

    @Override
    public Optional<StrategyBindingsEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<StrategyBindingsEntity> findAll() {
        return null;
    }
}
