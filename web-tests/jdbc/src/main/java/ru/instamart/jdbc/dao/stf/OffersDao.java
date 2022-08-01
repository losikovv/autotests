package ru.instamart.jdbc.dao.stf;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.OffersEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

@Slf4j
public final class OffersDao extends AbstractDao<Long, OffersEntity> {

    public static final OffersDao INSTANCE = new OffersDao();

    private static final String SELECT_SQL = "SELECT %s FROM offers ";
    private final String DELETE_SQL = "DELETE FROM offers ";
    private final String UPDATE_SQL = "UPDATE offers ";

    public int getSoldProduct() {
        try (final var connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "id") + "WHERE stock = 0 AND published = 0 LIMIT 1");
             final var resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt("id");
        } catch (SQLException e) {
            log.error("FATAL: Can't obtain id");
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return 0;
    }

    public OffersEntity getOfferByStoreId(Integer storeId) {
        OffersEntity offer = new OffersEntity();
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + "WHERE store_id = ? AND published = 1 AND deleted_at IS NULL LIMIT 1")) {
            preparedStatement.setInt(1, storeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            offer.setId(resultSet.getLong("id"));
            offer.setProductId(resultSet.getLong("product_id"));
            offer.setProductSku(resultSet.getString("product_sku"));
            offer.setRetailerSku(resultSet.getString("retailer_sku"));
            offer.setRetailerId(resultSet.getInt("retailer_id"));
            offer.setName(resultSet.getString("name"));
            offer.setPublished(resultSet.getInt("published"));
            offer.setStock(resultSet.getInt("stock"));
            offer.setUuid(resultSet.getString("uuid"));
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return offer;
    }

    @Override
    public Optional<OffersEntity> findById(Long id) {
        OffersEntity offer = null;
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement( String.format(SELECT_SQL, "*") + " WHERE id = ?")) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                offer = new OffersEntity();
                offer.setId(resultSet.getLong("id"));
                offer.setProductId(resultSet.getLong("product_id"));
                offer.setProductSku(resultSet.getString("product_sku"));
                offer.setRetailerSku(resultSet.getString("retailer_sku"));
                offer.setRetailerId(resultSet.getInt("retailer_id"));
                offer.setName(resultSet.getString("name"));
                offer.setPublished(resultSet.getInt("published"));
                offer.setStock(resultSet.getInt("stock"));
                offer.setUuid(resultSet.getString("uuid"));
                offer.setDeletedAt(resultSet.getTimestamp("deleted_at"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(offer);
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


    public boolean deleteByStoreId(Integer storeId) {
        int result = 0;
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE store_id = ?")) {
            preparedStatement.setLong(1, storeId);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return result == 1;
    }

    public void updateOfferStock(Long offerId, Integer stock) {
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_SQL + "SET stock = ? WHERE id = ?")) {
            preparedStatement.setInt(1, stock);
            preparedStatement.setLong(2, offerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }

    public OffersEntity getSkuByPricer(long retailerId, int storeId, long shippingCategoryId, String pricer) {
        OffersEntity offersEntity = new OffersEntity();
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "o.*") +
                     " o JOIN spree_products sp ON sp.id = o.product_id WHERE o.store_id = ? AND sp.shipping_category_id = ? " +
                     "AND o.retailer_id = ? AND o.pricer = ? AND o.deleted_at is NULL AND sp.deleted_at is NULL AND o.published = 1 ORDER BY o.id DESC LIMIT 1")) {
            preparedStatement.setInt(1, storeId);
            preparedStatement.setLong(2, shippingCategoryId);
            preparedStatement.setLong(3, retailerId);
            preparedStatement.setString(4, pricer);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            offersEntity.setProductSku(resultSet.getString("product_sku"));
            offersEntity.setRetailerSku(resultSet.getString("retailer_sku"));
            offersEntity.setId(resultSet.getLong("id"));
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return offersEntity;
    }

    public OffersEntity getOfferWithSpecificPrice(int storeId, double price) {
        OffersEntity offersEntity = new OffersEntity();
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "o.*") +
                     " o JOIN prices p ON o.id = p.offer_id JOIN spree_products sp ON sp.id = o.product_id " +
                     "WHERE o.store_id = ? AND p.price < ? AND o.deleted_at is NULL AND sp.deleted_at is NULL AND o.published = 1 ORDER BY o.id DESC LIMIT 1")) {
            preparedStatement.setInt(1, storeId);
            preparedStatement.setDouble(2, price);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            offersEntity.setProductSku(resultSet.getString("product_sku"));
            offersEntity.setRetailerSku(resultSet.getString("retailer_sku"));
            offersEntity.setId(resultSet.getLong("id"));
            offersEntity.setProductId(resultSet.getLong("product_id"));
            offersEntity.setUuid(resultSet.getString("uuid"));
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return offersEntity;
    }
}
