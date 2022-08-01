package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeBrandsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreeBrandsDao extends AbstractDao<Long, SpreeBrandsEntity> {

    public static final SpreeBrandsDao INSTANCE = new SpreeBrandsDao();
    private static final String SELECT_SQL = "SELECT %s FROM spree_brands";
    private final String DELETE_SQL = "DELETE FROM spree_brands";

    public SpreeBrandsEntity getBrandByName(String brandName) {
        SpreeBrandsEntity brand = new SpreeBrandsEntity();
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE name = ?")) {
            preparedStatement.setString(1, brandName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                brand.setId(resultSet.getLong("id"));
                brand.setKeywords(resultSet.getString("keywords"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return brand;
    }

    @Override
    public boolean delete(Long id) {
        int result = 0;
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return result == 1;
    }
}
