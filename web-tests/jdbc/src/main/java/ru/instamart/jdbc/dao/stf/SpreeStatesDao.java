package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeStatesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SpreeStatesDao extends AbstractDao<Long, SpreeStatesEntity> {

    public static final SpreeStatesDao INSTANCE = new SpreeStatesDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_states";

    public SpreeStatesEntity getStateByAbbr(String stateAbbr) {
        SpreeStatesEntity state = new SpreeStatesEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE abbr = ?")) {
            preparedStatement.setString(1, stateAbbr);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                state.setId(resultSet.getLong("id"));
                state.setName(resultSet.getString("name"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return state;
    }

    @Override
    public Optional<SpreeStatesEntity> findById(Long id) {
        SpreeStatesEntity spreeStatesEntity = null;
        var sql = String.format(SELECT_SQL, "*") + " WHERE id = ?";
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                spreeStatesEntity = new SpreeStatesEntity();
                spreeStatesEntity.setId(resultSet.getLong("id"));
                spreeStatesEntity.setName(resultSet.getString("name"));
                spreeStatesEntity.setAbbr(resultSet.getString("abbr"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(spreeStatesEntity);
    }

    public int getCount() {
        int resultCount = 0;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total"))) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            resultCount = resultSet.getInt("total");
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return resultCount;
    }
}
