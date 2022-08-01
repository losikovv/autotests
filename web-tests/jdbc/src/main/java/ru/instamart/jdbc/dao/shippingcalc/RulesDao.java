package ru.instamart.jdbc.dao.shippingcalc;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.shippingcalc.RulesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class RulesDao implements Dao<Integer, RulesEntity> {
    public static final RulesDao INSTANCE = new RulesDao();
    private final String SELECT_SQL = "SELECT %s FROM rules ";
    private final String INSERT_SQL = "INSERT INTO rules ";
    private final String DELETE_SQL = "DELETE FROM rules ";

    public List<RulesEntity> getRules(Integer id) {
        List<RulesEntity> rulesResult = new ArrayList<>();
        try (Connection connect = ConnectionManager.getConnection(Db.PG_SHIPPING_CALC);
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
        try (Connection connect = ConnectionManager.getConnection(Db.PG_SHIPPING_CALC);
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

    public Integer addRule(Integer strategyId, Integer scriptId, String scriptParams, Integer priority, String creatorId, String ruleType) {
        try (Connection connect = ConnectionManager.getConnection(Db.PG_SHIPPING_CALC);
             PreparedStatement preparedStatement = connect.prepareStatement(INSERT_SQL + " (strategy_id, script_id, script_params, priority, creator_id, rule_type) " +
                     " VALUES (?, ?, ?::jsonb, ?, ?, ?::rule_type) ", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, strategyId);
            preparedStatement.setInt(2, scriptId);
            preparedStatement.setString(3, scriptParams);
            preparedStatement.setInt(4, priority);
            preparedStatement.setString(5, creatorId);
            preparedStatement.setString(6, ruleType);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connect = ConnectionManager.getConnection(Db.PG_SHIPPING_CALC);
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
