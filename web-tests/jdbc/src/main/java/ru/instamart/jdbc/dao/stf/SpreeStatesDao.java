package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeStatesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SpreeStatesDao extends AbstractDao<Long, SpreeStatesEntity> {

    public static final SpreeStatesDao INSTANCE = new SpreeStatesDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_states";

    public SpreeStatesEntity getStateByAbbr(String stateAbbr) {
        final var state = new SpreeStatesEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE abbr = ?")) {
            preparedStatement.setString(1, stateAbbr);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    state.setId(resultSet.getLong("id"));
                    state.setName(resultSet.getString("name"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return state;
    }

    @Override
    public Optional<SpreeStatesEntity> findById(Long id) {
        SpreeStatesEntity spreeStatesEntity = null;
        var sql = String.format(SELECT_SQL, "*") + " WHERE id = ?";
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    spreeStatesEntity = new SpreeStatesEntity();
                    spreeStatesEntity.setId(resultSet.getLong("id"));
                    spreeStatesEntity.setName(resultSet.getString("name"));
                    spreeStatesEntity.setAbbr(resultSet.getString("abbr"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(spreeStatesEntity);
    }

    public int getCount() {
        int resultCount = 0;
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total"));
             final var resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                resultCount = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return resultCount;
    }
}
