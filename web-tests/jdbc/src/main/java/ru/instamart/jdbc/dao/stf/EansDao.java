package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.EansEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class EansDao extends AbstractDao<Long, EansEntity> {

    public static final EansDao INSTANCE = new EansDao();
    private static final String SELECT_SQL = "SELECT %s FROM eans";
    private final String DELETE_SQL = "DELETE FROM eans";

    public EansEntity getEanByRetailerSku(String retailerSku) {
        EansEntity ean = new EansEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE offer_retailer_sku = ?")) {
            preparedStatement.setString(1, retailerSku);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    ean.setId(resultSet.getLong("id"));
                    ean.setValue(resultSet.getString("value"));
                    ean.setRetailerId(resultSet.getLong("retailer_id"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return ean;
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
