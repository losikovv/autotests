package ru.instamart.jdbc.dao.shopper;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.shopper.RetailersShopperEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class RetailersShopperDao extends AbstractDao<Long, RetailersShopperEntity> {
    public static final RetailersShopperDao INSTANCE = new RetailersShopperDao();
    private final String DELETE_SQL = "DELETE FROM retailers ";

    public void deleteRetailerByNameFromShopper(String retailerName) {
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_SHP).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + "WHERE name = ?")) {
            preparedStatement.setString(1, retailerName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLManager. Error: " + e.getMessage());
        }
    }
}
