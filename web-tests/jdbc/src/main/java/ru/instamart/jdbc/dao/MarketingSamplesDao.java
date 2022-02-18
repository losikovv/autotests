package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.MarketingSamplesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class MarketingSamplesDao extends AbstractDao<Long, MarketingSamplesEntity> {

    public static final MarketingSamplesDao INSTANCE = new MarketingSamplesDao();
    private final String SELECT_SQL = "SELECT %s FROM marketing_samples";


    public Optional<MarketingSamplesEntity> findByIdWithUser(Long id) {
        MarketingSamplesEntity marketingSamplesEntity = new MarketingSamplesEntity();
        var sql = String.format(SELECT_SQL, "*") + " s JOIN marketing_samples_users u ON s.id = u.marketing_sample_id WHERE s.id = ?";
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                marketingSamplesEntity.setName(resultSet.getString("name"));
                marketingSamplesEntity.setDescription(resultSet.getString("description"));
                marketingSamplesEntity.setComment(resultSet.getString("comment"));
                marketingSamplesEntity.setUserId(resultSet.getLong("user_id"));
                marketingSamplesEntity.setDeletedAt(resultSet.getString("deleted_at"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.of(marketingSamplesEntity);
    }

    @Override
    public Optional<MarketingSamplesEntity> findById(Long id) {
        MarketingSamplesEntity marketingSamplesEntity = new MarketingSamplesEntity();
        var sql = String.format(SELECT_SQL, "*") + " WHERE id = ?";
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                marketingSamplesEntity.setName(resultSet.getString("name"));
                marketingSamplesEntity.setDescription(resultSet.getString("description"));
                marketingSamplesEntity.setComment(resultSet.getString("comment"));
                marketingSamplesEntity.setDeletedAt(resultSet.getString("deleted_at"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.of(marketingSamplesEntity);
    }

    public int getCount() {
        int resultCount = 0;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total") + " WHERE deleted_at IS NULL")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            resultCount = resultSet.getInt("total");
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return resultCount;
    }
}
