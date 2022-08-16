package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeManufacturingCountriesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreeManufacturingCountriesDao extends AbstractDao<Long, SpreeManufacturingCountriesEntity> {

    public static final SpreeManufacturingCountriesDao INSTANCE = new SpreeManufacturingCountriesDao();
    private final String SELECT_SQL = "SELECT * FROM spree_manufacturing_countries";

    public  SpreeManufacturingCountriesEntity getManufacturingCountryByName(String name) {
        final var manufacturingCountry = new SpreeManufacturingCountriesEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(SELECT_SQL +
                     " WHERE name = ?")) {
            preparedStatement.setString(1, name);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    manufacturingCountry.setId(resultSet.getLong("id"));
                    manufacturingCountry.setPermalink(resultSet.getString("permalink"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return manufacturingCountry;
    }
}
