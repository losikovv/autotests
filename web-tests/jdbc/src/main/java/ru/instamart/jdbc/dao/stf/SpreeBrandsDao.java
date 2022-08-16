package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeBrandsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreeBrandsDao extends AbstractDao<Long, SpreeBrandsEntity> {

    public static final SpreeBrandsDao INSTANCE = new SpreeBrandsDao();
    private static final String SELECT_SQL = "SELECT %s FROM spree_brands";
    private final String DELETE_SQL = "DELETE FROM spree_brands";

    public SpreeBrandsEntity getBrandByName(String brandName) {
        final var brand = new SpreeBrandsEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE name = ?")) {
            preparedStatement.setString(1, brandName);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    brand.setId(resultSet.getLong("id"));
                    brand.setName(resultSet.getString("name"));
                    brand.setCreatedAt(resultSet.getString("created_at"));
                    brand.setUpdatedAt(resultSet.getString("updated_at"));
                    brand.setPermalink(resultSet.getString("permalink"));
                    brand.setKeywords(resultSet.getString("keywords"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return brand;
    }

    public SpreeBrandsEntity getBrandById(Long id) {
        final var brand = new SpreeBrandsEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    brand.setId(resultSet.getLong("id"));
                    brand.setName(resultSet.getString("name"));
                    brand.setCreatedAt(resultSet.getString("created_at"));
                    brand.setUpdatedAt(resultSet.getString("updated_at"));
                    brand.setPermalink(resultSet.getString("permalink"));
                    brand.setKeywords(resultSet.getString("keywords"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return brand;
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
