package ru.instamart.jdbc.dao.self_fee;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.self_fee.FlagsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class FlagsDao extends AbstractDao<Integer, FlagsEntity> {

    public static final FlagsDao INSTANCE = new FlagsDao();
    private final String UPDATE_SQL = "UPDATE public.flags ";

    public boolean update(final String key, final String value) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SELF_FEE).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(UPDATE_SQL) + " SET value=? WHERE \"key\"=?")) {
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, key);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Optional<FlagsEntity> findById(Integer id) {
        return Optional.empty();
    }
}
