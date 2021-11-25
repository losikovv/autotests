package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.CitiesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CitiesDao implements Dao<Long, CitiesEntity>{

    public static final CitiesDao INSTANCE = new CitiesDao();
    private final String SELECT_SQL = "SELECT * FROM cities";
    private final String DELETE_SQL = "DELETE FROM cities";

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public CitiesEntity save(CitiesEntity ticket) {
        return null;
    }

    @Override
    public void update(CitiesEntity ticket) {

    }

    @Override
    public Optional<CitiesEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<CitiesEntity> findAll() {
        return null;
    }

    public CitiesEntity getCityByName(String cityName) {
        CitiesEntity city = new CitiesEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(SELECT_SQL +
                   " WHERE name = ?")) {
            preparedStatement.setString(1, cityName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                city.setId(resultSet.getLong("id"));
                city.setSlug(resultSet.getString("slug"));
                city.setNameIn(resultSet.getString("name_in"));
                city.setNameTo(resultSet.getString("name_to"));
                city.setNameFrom(resultSet.getString("name_from"));
                city.setLocked(resultSet.getInt("locked"));
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    public void deleteCityByName(String cityName) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE name = ?")) {
            preparedStatement.setString(1, cityName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
