package ru.instamart.jdbc.dao.surgelevel;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.surgelevel.ResultEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class ResultDao extends AbstractDao<String, ResultEntity> {

    public static final ResultDao INSTANCE = new ResultDao();


    public ResultEntity findResult(String storeId) {
        final var result = new ResultEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SURGE_LEVEL).getConnection();
             final var preparedStatement = connect.prepareStatement(" SELECT * FROM result WHERE id = (SELECT actual_result_id FROM store WHERE id = ?::uuid) ")) {
            preparedStatement.setString(1, storeId);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result.setId(resultSet.getString("id"));
                    result.setSurgeLevel(resultSet.getInt("surge_level"));
                    result.setCreatedAt(resultSet.getString("created_at"));
                    result.setStartedAt(resultSet.getString("started_at"));
                    result.setExpiredAt(resultSet.getString("expired_at"));
                    result.setMethod(resultSet.getInt("method"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLSurgelevelManager. Error: " + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Optional<ResultEntity> findById(String id) {
        return Optional.empty();
    }
}
