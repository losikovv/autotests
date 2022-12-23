package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.CitiesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class CitiesDao extends AbstractDao<Long, CitiesEntity> {

    public static final CitiesDao INSTANCE = new CitiesDao();
    private final String SELECT_SQL = "SELECT %s FROM cities";
    private final String DELETE_SQL = "DELETE FROM cities";

    public CitiesEntity getCityByName(String cityName) {
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE name = ?")) {
            preparedStatement.setString(1, cityName);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    final var city = new CitiesEntity();
                    city.setId(resultSet.getLong("id"));
                    city.setSlug(resultSet.getString("slug"));
                    city.setNameIn(resultSet.getString("name_in"));
                    city.setNameTo(resultSet.getString("name_to"));
                    city.setNameFrom(resultSet.getString("name_from"));
                    city.setLocked(resultSet.getInt("locked"));
                    return city;
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return null;
    }

    public void deleteCityByName(String cityName) {
        try (Connection connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE name = ?")) {
            preparedStatement.setString(1, cityName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }

    public int getCount() {
        int resultCount = 0;
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total"))) {
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

    @Override
    public boolean delete(Long id) {
        int result = 0;
        try (Connection connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
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
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    city = new CitiesEntity();
                    city.setId(resultSet.getLong("id"));
                    city.setSlug(resultSet.getString("slug"));
                    city.setNameIn(resultSet.getString("name_in"));
                    city.setNameTo(resultSet.getString("name_to"));
                    city.setNameFrom(resultSet.getString("name_from"));
                    city.setLocked(resultSet.getInt("locked"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(city);
    }
}
