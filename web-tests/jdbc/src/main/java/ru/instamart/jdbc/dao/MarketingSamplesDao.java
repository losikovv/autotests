package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.MarketingSamplesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MarketingSamplesDao implements Dao<Long, MarketingSamplesEntity> {

    public static final MarketingSamplesDao INSTANCE = new MarketingSamplesDao();
    private final String SELECT_SQL = "SELECT %s FROM marketing_samples";

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public MarketingSamplesEntity save(MarketingSamplesEntity ticket) {
        return null;
    }

    @Override
    public void update(MarketingSamplesEntity ticket) {
    }

    @Override
    public Optional<MarketingSamplesEntity> findById(Long id) {
       MarketingSamplesEntity marketingSamplesEntity = new MarketingSamplesEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " s JOIN marketing_samples_users u ON s.id = u.marketing_sample_id WHERE id = " + id)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                marketingSamplesEntity.setName(resultSet.getString("name"));
                marketingSamplesEntity.setDescription(resultSet.getString("description"));
                marketingSamplesEntity.setComment(resultSet.getString("comment"));
                marketingSamplesEntity.setUserId(resultSet.getLong("user_id"));
                marketingSamplesEntity.setDeletedAt(resultSet.getString("deleted_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(marketingSamplesEntity);
    }

    @Override
    public List<MarketingSamplesEntity> findAll() {
        return null;
    }

    public int getCount() {
        int resultCount = 0;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*)") + " WHERE deleted_at IS NULL")) {
             ResultSet resultSet = preparedStatement.executeQuery();
             resultSet.next();
             resultCount = resultSet.getInt("count(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultCount;
    }
}
