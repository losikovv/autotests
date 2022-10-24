package ru.instamart.jdbc.dao.surgelevel;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.surgelevel.FormulaEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class FormulaDao extends AbstractDao<String, FormulaEntity> {

    public static final FormulaDao INSTANCE = new FormulaDao();

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
