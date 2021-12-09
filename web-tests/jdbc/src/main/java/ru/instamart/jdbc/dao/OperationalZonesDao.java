package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.OperationalZonesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OperationalZonesDao implements Dao<Long, OperationalZonesEntity> {

    public static final OperationalZonesDao INSTANCE = new OperationalZonesDao();
    private final String DELETE_SQL = "DELETE FROM operational_zones ";
    private final String SELECT_SQL = "SELECT %s FROM operational_zones ";

    @Override
    public boolean delete(Long id) {
        int result = 0;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + String.format("WHERE id = '%s'", id))) {
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result == 1;
    }

    @Override
    public OperationalZonesEntity save(OperationalZonesEntity ticket) {
        return null;
    }

    @Override
    public void update(OperationalZonesEntity ticket) {

    }

    @Override
    public Optional<OperationalZonesEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<OperationalZonesEntity> findAll() {
        return null;
    }

    public void deleteZoneByName(String zoneName) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + "WHERE name = ?")) {
            preparedStatement.setString(1, zoneName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public Long getIdByName(String name) {
        Long id = null;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "id") + " WHERE name = ?")) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getLong("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
