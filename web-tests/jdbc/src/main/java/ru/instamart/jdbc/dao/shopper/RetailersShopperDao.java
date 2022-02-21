package ru.instamart.jdbc.dao.shopper;

import ru.instamart.jdbc.util.ConnectionPgSQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class RetailersShopperDao {
    public static final RetailersShopperDao INSTANCE = new RetailersShopperDao();
    private final String DELETE_SQL = "DELETE FROM retailers ";

    public void deleteRetailerByNameFromShopper(String retailerName) {
        try (Connection connect = ConnectionPgSQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + "WHERE name = ?")) {
            preparedStatement.setString(1, retailerName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLManager. Error: " + e.getMessage());
        }
    }
}
