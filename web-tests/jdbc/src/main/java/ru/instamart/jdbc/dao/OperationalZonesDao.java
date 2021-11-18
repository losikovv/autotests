package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.OperationalZonesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OperationalZonesDao implements Dao<Long, OperationalZonesEntity> {

    public static final OperationalZonesDao INSTANCE = new OperationalZonesDao();
    private final String DELETE_SQL = "DELETE FROM operational_zones ";

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
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + String.format("WHERE name = '%s'", zoneName))) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
