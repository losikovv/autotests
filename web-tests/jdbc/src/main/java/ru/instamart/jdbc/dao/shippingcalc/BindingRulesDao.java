package ru.instamart.jdbc.dao.shippingcalc;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.shippingcalc.BindingRulesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class BindingRulesDao implements Dao<Integer, BindingRulesEntity> {
    public static final BindingRulesDao INSTANCE = new BindingRulesDao();

    public BindingRulesEntity getBindingRuleById(Integer id) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             final var preparedStatement = connect.prepareStatement(" SELECT * FROM binding_rules WHERE id = ? ")) {
            preparedStatement.setInt(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    final var rule = new BindingRulesEntity();
                    rule.setId(resultSet.getInt("id"));
                    rule.setStrategyId(resultSet.getInt("strategy_id"));
                    rule.setShipping(resultSet.getString("shipping"));
                    rule.setTenantId(resultSet.getString("tenant_id"));
                    rule.setRegionId(resultSet.getInt("region_id"));
                    rule.setRetailerId(resultSet.getInt("retailer_id"));
                    rule.setOndemand(resultSet.getBoolean("ondemand"));
                    rule.setLabelId(resultSet.getInt("label_id"));
                    rule.setCreatedAt(resultSet.getString("created_at"));
                    rule.setUpdatedAt(resultSet.getString("updated_at"));
                    rule.setDeletedAt(resultSet.getString("deleted_at"));
                    rule.setDescription(resultSet.getString("description"));
                    return rule;
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return null;
    }

    public BindingRulesEntity getBindingRuleByStrategyAndTenant(Integer strategyId, String tenantId) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             final var preparedStatement = connect.prepareStatement(" SELECT * FROM binding_rules WHERE strategy_id = ? AND tenant_id = ? ")) {
            preparedStatement.setInt(1, strategyId);
            preparedStatement.setString(2, tenantId);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    final var rule = new BindingRulesEntity();
                    rule.setId(resultSet.getInt("id"));
                    rule.setStrategyId(resultSet.getInt("strategy_id"));
                    rule.setShipping(resultSet.getString("shipping"));
                    rule.setTenantId(resultSet.getString("tenant_id"));
                    rule.setRegionId(resultSet.getInt("region_id"));
                    rule.setRetailerId(resultSet.getInt("retailer_id"));
                    rule.setOndemand(resultSet.getBoolean("ondemand"));
                    rule.setLabelId(resultSet.getInt("label_id"));
                    rule.setCreatedAt(resultSet.getString("created_at"));
                    rule.setUpdatedAt(resultSet.getString("updated_at"));
                    rule.setDeletedAt(resultSet.getString("deleted_at"));
                    rule.setDescription(resultSet.getString("description"));
                    return rule;
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return null;
    }

    public List<Integer> getActiveBindingRulesList() {
        List<Integer> bindingRulesResult = new ArrayList<>();
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             final var preparedStatement = connect.prepareStatement(" SELECT id FROM binding_rules WHERE deleted_at IS NULL ")) {
            try (final var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    bindingRulesResult.add(resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return bindingRulesResult;
    }

    public Integer addBindingRule(Integer strategyId, String shipping, String tenantId, Integer regionId, Integer retailerId, Boolean ondemand, Integer labelId, String deletedAt) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             final var preparedStatement = connect.prepareStatement(" INSERT INTO binding_rules (strategy_id, shipping, tenant_id, region_id, retailer_id, ondemand, label_id, deleted_at) " +
                     " VALUES (?, ?::delivery_type, ?, ?, ?, ?, ?, ?::timestamp) ", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, strategyId);
            preparedStatement.setString(2, shipping);
            preparedStatement.setObject(3, tenantId);
            preparedStatement.setObject(4, regionId);
            preparedStatement.setObject(5, retailerId);
            preparedStatement.setObject(6, ondemand);
            preparedStatement.setObject(7, labelId);
            preparedStatement.setString(8, deletedAt);
            preparedStatement.executeUpdate();
            try (final var resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return 0;
    }

    public boolean updateBindingRulesState(String deletedAt, List<Integer> bindingRulesList) {
        final var builder = new StringBuilder();
        final var params = builder.append("?,".repeat(bindingRulesList.size())).deleteCharAt(builder.length() - 1).toString();
        final var updateSql = " UPDATE binding_rules SET deleted_at = ?::timestamp WHERE id IN (" + params + ") ";

        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             final var preparedStatement = connect.prepareStatement(updateSql)) {
            preparedStatement.setObject(1, deletedAt);
            int index = 2;
            for (Object ruleId : bindingRulesList) preparedStatement.setObject(index++, ruleId);
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
    public BindingRulesEntity save(BindingRulesEntity ticket) {
        return null;
    }

    @Override
    public void update(BindingRulesEntity ticket) {

    }

    @Override
    public Optional<BindingRulesEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<BindingRulesEntity> findAll() {
        return null;
    }
}
