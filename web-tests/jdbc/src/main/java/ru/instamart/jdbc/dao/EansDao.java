package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.EansEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class EansDao extends AbstractDao<Long, EansEntity> {

    public static final EansDao INSTANCE = new EansDao();
    private static final String SELECT_SQL = "SELECT %s FROM eans";
    private final String DELETE_SQL = "DELETE FROM eans";

    public EansEntity getEanByRetailerSku(String retailerSku) {
        EansEntity ean = new EansEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE offer_retailer_sku = ?")) {
            preparedStatement.setString(1, retailerSku);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                ean.setId(resultSet.getLong("id"));
                ean.setValue(resultSet.getString("value"));
                ean.setRetailerId(resultSet.getLong("retailer_id"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return ean;
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
}
