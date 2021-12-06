package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.StoresEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StoresDao implements Dao<Long, StoresEntity> {

    public static final StoresDao INSTANCE = new StoresDao();
    private final String SELECT_SQL = "SELECT %s FROM stores";

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public StoresEntity save(StoresEntity ticket) {
        return null;
    }

    @Override
    public void update(StoresEntity ticket) {

    }

    @Override
    public Optional<StoresEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<StoresEntity> findAll() {
        return null;
    }

    public int getCount() {
        int resultCount = 0;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total"))) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            resultCount = resultSet.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultCount;
    }
}
