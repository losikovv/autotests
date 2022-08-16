package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.MarketingSamplesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class MarketingSamplesDao extends AbstractDao<Long, MarketingSamplesEntity> {

    public static final MarketingSamplesDao INSTANCE = new MarketingSamplesDao();
    private final String SELECT_SQL = "SELECT %s FROM marketing_samples";


    public Optional<MarketingSamplesEntity> findByIdWithUser(Long id) {
        final var marketingSamplesEntity = new MarketingSamplesEntity();
        final var sql = String.format(SELECT_SQL, "*") + " s JOIN marketing_samples_users u ON s.id = u.marketing_sample_id WHERE s.id = ?";

        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    marketingSamplesEntity.setName(resultSet.getString("name"));
                    marketingSamplesEntity.setDescription(resultSet.getString("description"));
                    marketingSamplesEntity.setComment(resultSet.getString("comment"));
                    marketingSamplesEntity.setUserId(resultSet.getLong("user_id"));
                    marketingSamplesEntity.setDeletedAt(resultSet.getString("deleted_at"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.of(marketingSamplesEntity);
    }

    @Override
    public Optional<MarketingSamplesEntity> findById(Long id) {
        final var marketingSamplesEntity = new MarketingSamplesEntity();
        final var sql = String.format(SELECT_SQL, "*") + " WHERE id = ?";

        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    marketingSamplesEntity.setName(resultSet.getString("name"));
                    marketingSamplesEntity.setDescription(resultSet.getString("description"));
                    marketingSamplesEntity.setComment(resultSet.getString("comment"));
                    marketingSamplesEntity.setDeletedAt(resultSet.getString("deleted_at"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.of(marketingSamplesEntity);
    }

    public int getCount() {
        int resultCount = 0;
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total") + " WHERE deleted_at IS NULL")) {
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    resultCount = resultSet.getInt("total");
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return resultCount;
    }
}
