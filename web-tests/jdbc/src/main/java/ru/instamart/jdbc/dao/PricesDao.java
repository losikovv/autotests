package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.PricesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class PricesDao extends AbstractDao<Long, PricesEntity> {

    public static final PricesDao INSTANCE = new PricesDao();
    private static final String SELECT_SQL = "SELECT %s FROM prices";
    private final String DELETE_SQL = "DELETE FROM prices";

    public PricesEntity getPriceByOfferId(Long offerId) {
        PricesEntity price = new PricesEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE offer_id = ?")) {
            preparedStatement.setLong(1, offerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                price.setId(resultSet.getLong("id"));
                price.setProductSku(resultSet.getString("product_sku"));
                price.setStoreId(resultSet.getLong("store_id"));
                price.setTenantId(resultSet.getString("tenant_id"));
                price.setRetailerPrice(resultSet.getDouble("retailer_price"));
                price.setOfferPrice(resultSet.getDouble("offer_price"));
                price.setOfferRetailerPrice(resultSet.getDouble("offer_retailer_price"));
                price.setCostPrice(resultSet.getDouble("cost_price"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return price;
    }

    public void deletePriceByOfferId(Long offerId) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE offer_id = ?")) {
            preparedStatement.setLong(1, offerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
