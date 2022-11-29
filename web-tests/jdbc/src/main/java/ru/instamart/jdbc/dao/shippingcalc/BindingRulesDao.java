package ru.instamart.jdbc.dao.shippingcalc;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.shippingcalc.BindingRulesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class BindingRulesDao implements Dao<Integer, BindingRulesEntity> {
    public static final BindingRulesDao INSTANCE = new BindingRulesDao();

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
