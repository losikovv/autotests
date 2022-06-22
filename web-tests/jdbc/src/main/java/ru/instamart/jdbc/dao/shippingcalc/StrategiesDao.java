package ru.instamart.jdbc.dao.shippingcalc;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.shippingcalc.StrategiesEntity;
import ru.instamart.jdbc.util.dispatch.ConnectionPgSQLShippingCalcManager;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class StrategiesDao implements Dao<Integer, StrategiesEntity> {
    public static final StrategiesDao INSTANCE = new StrategiesDao();
    private final String SELECT_SQL = "SELECT %s FROM strategies ";
    private final String INSERT_SQL = "INSERT INTO strategies ";
    private final String DELETE_SQL = "DELETE FROM strategies ";

    public StrategiesEntity getStrategy(Integer id) {
        StrategiesEntity strategy = new StrategiesEntity();
        try (Connection connect = ConnectionPgSQLShippingCalcManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE id = ? ")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                strategy.setId(resultSet.getInt("id"));
                strategy.setName(resultSet.getString("name"));
                strategy.setShipping(resultSet.getString("shipping"));
                strategy.setGlobal(resultSet.getBoolean("global"));
                strategy.setPriority(resultSet.getInt("priority"));
                strategy.setDescription(resultSet.getString("description"));
                strategy.setCreatedAt(resultSet.getString("created_at"));
                strategy.setUpdatedAt(resultSet.getString("updated_at"));
                strategy.setCreatorId(resultSet.getString("creator_id"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return strategy;
    }

    public Integer addStrategy(String name, String shipping, Boolean global, Integer priority, String description, String creatorId) {
        try (Connection connect = ConnectionPgSQLShippingCalcManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(INSERT_SQL + " (name, shipping, global, priority, description, creator_id) " +
                     " VALUES (?, ?::delivery_type, ?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, shipping);
            preparedStatement.setBoolean(3, global);
            preparedStatement.setInt(4, priority);
            preparedStatement.setString(5, description);
            preparedStatement.setString(6, creatorId);
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
        try (Connection connect = ConnectionPgSQLShippingCalcManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE id = ? ")) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public StrategiesEntity save(StrategiesEntity ticket) {
        return null;
    }

    @Override
    public void update(StrategiesEntity ticket) {

    }

    @Override
    public Optional<StrategiesEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<StrategiesEntity> findAll() {
        return null;
    }
}
