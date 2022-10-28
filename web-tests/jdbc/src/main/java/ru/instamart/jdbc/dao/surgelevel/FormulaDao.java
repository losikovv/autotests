package ru.instamart.jdbc.dao.surgelevel;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.surgelevel.FormulaEntity;
import ru.instamart.jdbc.entity.surgelevel.SupplyEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class FormulaDao extends AbstractDao<String, FormulaEntity> {

    public static final FormulaDao INSTANCE = new FormulaDao();

    public FormulaEntity findFormula(String formulaId) {
        final var formula = new FormulaEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SURGE_LEVEL).getConnection();
             final var preparedStatement = connect.prepareStatement(" SELECT * FROM formula WHERE id = ?::uuid ")) {
            preparedStatement.setString(1, formulaId);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    formula.setId(resultSet.getString("id"));
                    formula.setName(resultSet.getString("name"));
                    formula.setScript(resultSet.getString("script"));
                    formula.setCreatedAt(resultSet.getString("created_at"));
                    formula.setUpdatedAt(resultSet.getString("updated_at"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLSurgelevelManager. Error: " + e.getMessage());
        }
        return formula;
    }

    public boolean addFormula(String formulaId, String name, String script){
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_SURGE_LEVEL).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(" INSERT INTO formula (id, name, script, created_at) " +
                     " VALUES (?::uuid, ?, ?, now()) ")) {
            preparedStatement.setString(1, formulaId);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, script);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLSurgelevelManager. Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(String formulaId) {
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_SURGE_LEVEL).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(" DELETE FROM formula WHERE id = ?::uuid ")) {
            preparedStatement.setString(1, formulaId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLSurgelevelManager. Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Optional<FormulaEntity> findById(String id) {
        return Optional.empty();
    }
}
