package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeProductFiltersEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreeProductFiltersDao extends AbstractDao<Long, SpreeProductFiltersEntity> {

    public static final SpreeProductFiltersDao INSTANCE = new SpreeProductFiltersDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_product_filters";
    private final String DELETE_SQL = "DELETE FROM spree_product_filters";

    public SpreeProductFiltersEntity getFilterByInstamartId(Long instamartId) {
        final var spreeProductsFilter = new SpreeProductFiltersEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE instamart_id = ?")) {
            preparedStatement.setLong(1, instamartId);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    spreeProductsFilter.setId(resultSet.getLong("id"));
                    spreeProductsFilter.setName(resultSet.getString("name"));
                    spreeProductsFilter.setPermalink(resultSet.getString("permalink"));
                    spreeProductsFilter.setKeywords(resultSet.getString("keywords"));
                    spreeProductsFilter.setPosition(resultSet.getInt("position"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return spreeProductsFilter;
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
}
