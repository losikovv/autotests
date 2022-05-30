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
    private final String SELECT_SQL = "SELECT %s FROM conditions ";

    public List<ConditionsEntity> getConditions(Integer firstId, Integer secondId) {
        List<ConditionsEntity> conditionsResult = new ArrayList<>();
        try (Connection connect = ConnectionPgSQLShippingCalcManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE rule_id >= ? AND rule_id <= ? ORDER BY rule_id ")) {
            preparedStatement.setInt(1, firstId);
            preparedStatement.setInt(2, secondId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
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
