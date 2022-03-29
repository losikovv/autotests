package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.CitiesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class CitiesDao extends AbstractDao<Long, CitiesEntity> {

    public static final CitiesDao INSTANCE = new CitiesDao();
    private final String SELECT_SQL = "SELECT %s FROM cities";
    private final String DELETE_SQL = "DELETE FROM cities";

    public CitiesEntity getCityByName(String cityName) {
        CitiesEntity city = new CitiesEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
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
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return city;
    }

    public void deleteCityByName(String cityName) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE name = ?")) {
            preparedStatement.setString(1, cityName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
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
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return resultCount;
    }

    @Override
    public boolean delete(Long id) {
        int result = 0;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return result == 1;
    }

    @Override
    public Optional<CitiesEntity> findById(Long id) {
        CitiesEntity city = null;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                city = new CitiesEntity();
                city.setId(resultSet.getLong("id"));
                city.setSlug(resultSet.getString("slug"));
                city.setNameIn(resultSet.getString("name_in"));
                city.setNameTo(resultSet.getString("name_to"));
                city.setNameFrom(resultSet.getString("name_from"));
                city.setLocked(resultSet.getInt("locked"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(city);
    }
}
