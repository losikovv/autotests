package ru.instamart.jdbc.dao.stf;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.OffersEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

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
        try (final var connect = ConnectionMySQLManager.get();
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
        try (Connection connect = ConnectionMySQLManager.get();
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
        try (Connection connect = ConnectionMySQLManager.get();
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
        try (Connection connect = ConnectionMySQLManager.get();
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
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE store_id = ?")) {
            preparedStatement.setLong(1, storeId);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return result == 1;
    }

    public void updateOfferStock(Long offerId, Integer stock) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_SQL + "SET stock = ? WHERE id = ?")) {
            preparedStatement.setInt(1, stock);
            preparedStatement.setLong(2, offerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
