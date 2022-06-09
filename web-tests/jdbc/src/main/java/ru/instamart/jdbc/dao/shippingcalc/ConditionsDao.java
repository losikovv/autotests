package ru.instamart.jdbc.dao.shippingcalc;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.shippingcalc.ConditionsEntity;
import ru.instamart.jdbc.util.dispatch.ConnectionPgSQLShippingCalcManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class ConditionsDao implements Dao<Integer, ConditionsEntity> {
    public static final ConditionsDao INSTANCE = new ConditionsDao();

    public List<ConditionsEntity> getConditions(ArrayList<Integer> rulesIds) {
        List<ConditionsEntity> conditionsResult = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        String params = builder.append("?,".repeat(rulesIds.size())).deleteCharAt(builder.length() - 1).toString();
        String selectSql = "SELECT * FROM conditions WHERE rule_id IN (" + params + ") ORDER BY rule_id";

        try (Connection connect = ConnectionPgSQLShippingCalcManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(selectSql)) {
            int index = 1;
            for (Object ruleId : rulesIds) preparedStatement.setObject(index++, ruleId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var conditionsEntity = new ConditionsEntity();
                conditionsEntity.setRuleId(resultSet.getInt("rule_id"));
                conditionsEntity.setParams(resultSet.getString("params"));
                conditionsEntity.setConditionType(resultSet.getString("condition_type"));
                conditionsResult.add(conditionsEntity);
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return conditionsResult;
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
