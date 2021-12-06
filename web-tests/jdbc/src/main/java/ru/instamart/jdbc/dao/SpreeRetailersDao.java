package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.SpreeRetailersEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SpreeRetailersDao implements Dao<Long, SpreeRetailersEntity> {

    public static final SpreeRetailersDao INSTANCE = new SpreeRetailersDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_retailers";

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public SpreeRetailersEntity save(SpreeRetailersEntity ticket) {
        return null;
    }

    @Override
    public void update(SpreeRetailersEntity ticket) {

    }

    @Override
    public Optional<SpreeRetailersEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<SpreeRetailersEntity> findAll() {
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
