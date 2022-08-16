package ru.instamart.jdbc.dao.shippingcalc;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.shippingcalc.ConditionsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class ConditionsDao implements Dao<Integer, ConditionsEntity> {
    public static final ConditionsDao INSTANCE = new ConditionsDao();
    private final String INSERT_SQL = "INSERT INTO conditions ";

    public List<ConditionsEntity> getConditions(ArrayList<Integer> rulesIds) {
        final var conditionsResult = new ArrayList<ConditionsEntity>();
        final var builder = new StringBuilder();
        final var params = builder.append("?,".repeat(rulesIds.size())).deleteCharAt(builder.length() - 1).toString();
        final var selectSql = "SELECT * FROM conditions WHERE rule_id IN (" + params + ") ORDER BY rule_id";

        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             final var preparedStatement = connect.prepareStatement(selectSql)) {
            int index = 1;
            for (Object ruleId : rulesIds) preparedStatement.setObject(index++, ruleId);
            
            try (final var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    var conditionsEntity = new ConditionsEntity();
                    conditionsEntity.setRuleId(resultSet.getInt("rule_id"));
                    conditionsEntity.setParams(resultSet.getString("params"));
                    conditionsEntity.setConditionType(resultSet.getString("condition_type"));
                    conditionsResult.add(conditionsEntity);
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return conditionsResult;
    }

    public boolean addCondition(Integer ruleId, String params, String conditionType) {
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(INSERT_SQL + " (rule_id, params, condition_type) " +
                     " VALUES (?, ?::jsonb, ?::condition_type_enum) ")) {
            preparedStatement.setInt(1, ruleId);
            preparedStatement.setString(2, params);
            preparedStatement.setString(3, conditionType);
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
    public ConditionsEntity save(ConditionsEntity ticket) {
        return null;
    }

    @Override
    public void update(ConditionsEntity ticket) {

    }

    @Override
    public Optional<ConditionsEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<ConditionsEntity> findAll() {
        return null;
    }
}
