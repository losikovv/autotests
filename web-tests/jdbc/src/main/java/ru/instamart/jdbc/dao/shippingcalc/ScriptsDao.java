package ru.instamart.jdbc.dao.shippingcalc;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.shippingcalc.ScriptsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class ScriptsDao implements Dao<Integer, ScriptsEntity> {
    public static final ScriptsDao INSTANCE = new ScriptsDao();
    private final String SELECT_SQL = "SELECT %s FROM scripts ";
    private final String DELETE_SQL = "DELETE FROM scripts ";

    public ScriptsEntity getScriptByName(String name) {
        ScriptsEntity script = new ScriptsEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE name = ? ")) {
            preparedStatement.setString(1, name);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    script.setId(resultSet.getInt("id"));
                    script.setName(resultSet.getString("name"));
                    script.setCode(resultSet.getString("code"));
                    script.setCreatedAt(resultSet.getString("created_at"));
                    script.setUpdatedAt(resultSet.getString("updated_at"));
                    script.setDeletedAt(resultSet.getString("deleted_at"));
                    script.setState(resultSet.getString("state"));
                    script.setRequiredParams(resultSet.getString("required_params"));
                    script.setCreatorId(resultSet.getString("creator_id"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return script;
    }

    public ScriptsEntity getScriptById(Integer id) {
        ScriptsEntity script = new ScriptsEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE id = ? ")) {
            preparedStatement.setInt(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    script.setId(resultSet.getInt("id"));
                    script.setName(resultSet.getString("name"));
                    script.setCode(resultSet.getString("code"));
                    script.setCreatedAt(resultSet.getString("created_at"));
                    script.setUpdatedAt(resultSet.getString("updated_at"));
                    script.setDeletedAt(resultSet.getString("deleted_at"));
                    script.setState(resultSet.getString("state"));
                    script.setRequiredParams(resultSet.getString("required_params"));
                    script.setCreatorId(resultSet.getString("creator_id"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return script;
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE id = ? ")) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public ScriptsEntity save(ScriptsEntity ticket) {
        return null;
    }

    @Override
    public void update(ScriptsEntity ticket) {

    }

    @Override
    public Optional<ScriptsEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<ScriptsEntity> findAll() {
        return null;
    }
}
