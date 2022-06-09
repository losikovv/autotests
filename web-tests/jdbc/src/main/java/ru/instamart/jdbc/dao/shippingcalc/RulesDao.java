package ru.instamart.jdbc.dao.shippingcalc;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.shippingcalc.RulesEntity;
import ru.instamart.jdbc.util.dispatch.ConnectionPgSQLShippingCalcManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class RulesDao implements Dao<Integer, RulesEntity> {
    public static final RulesDao INSTANCE = new RulesDao();
    private final String SELECT_SQL = "SELECT %s FROM rules ";
    private final String DELETE_SQL = "DELETE FROM rules ";

    public List<RulesEntity> getRules(Integer id) {
        List<RulesEntity> rulesResult = new ArrayList<>();
        try (Connection connect = ConnectionPgSQLShippingCalcManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE strategy_id = ? ORDER BY id ")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var rulesEntity = new RulesEntity();
                rulesEntity.setId(resultSet.getInt("id"));
                rulesEntity.setStrategyId(resultSet.getInt("strategy_id"));
                rulesEntity.setScriptId(resultSet.getInt("script_id"));
                rulesEntity.setScriptParams(resultSet.getString("script_params"));
                rulesEntity.setPriority(resultSet.getInt("priority"));
                rulesEntity.setCreatedAt(resultSet.getString("created_at"));
                rulesEntity.setDeletedAt(resultSet.getString("deleted_at"));
                rulesEntity.setCreatorId(resultSet.getString("creator_id"));
                rulesEntity.setRuleType(resultSet.getString("rule_type"));
                rulesResult.add(rulesEntity);
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return rulesResult;
    }

    public List<RulesEntity> getDeletedRules(Integer id) {
        List<RulesEntity> rulesResult = new ArrayList<>();
        try (Connection connect = ConnectionPgSQLShippingCalcManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE strategy_id = ? AND deleted_at IS NOT NULL ORDER BY id ")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var rulesEntity = new RulesEntity();
                rulesEntity.setId(resultSet.getInt("id"));
                rulesEntity.setStrategyId(resultSet.getInt("strategy_id"));
                rulesEntity.setScriptId(resultSet.getInt("script_id"));
                rulesEntity.setScriptParams(resultSet.getString("script_params"));
                rulesEntity.setPriority(resultSet.getInt("priority"));
                rulesEntity.setCreatedAt(resultSet.getString("created_at"));
                rulesEntity.setDeletedAt(resultSet.getString("deleted_at"));
                rulesEntity.setCreatorId(resultSet.getString("creator_id"));
                rulesEntity.setRuleType(resultSet.getString("rule_type"));
                rulesResult.add(rulesEntity);
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return rulesResult;
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connect = ConnectionPgSQLShippingCalcManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE strategy_id = ? ")) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public RulesEntity save(RulesEntity ticket) {
        return null;
    }

    @Override
    public void update(RulesEntity ticket) {

    }

    @Override
    public Optional<RulesEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<RulesEntity> findAll() {
        return null;
    }
}
