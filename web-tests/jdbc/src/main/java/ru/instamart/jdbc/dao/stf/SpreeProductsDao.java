package ru.instamart.jdbc.dao.stf;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeProductsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

@Slf4j
public class SpreeProductsDao extends AbstractDao<Long, SpreeProductsEntity> {

    public static final SpreeProductsDao INSTANCE = new SpreeProductsDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_products";
    private final String DELETE_SQL = "DELETE FROM spree_products";

    public Long getOfferIdBySku(String sku, Integer storeId) {
        Long id = null;
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "o.id") +
                     " sp JOIN offers o ON sp.id = o.product_id WHERE sp.sku = ? AND o.store_id = ?")) {
            preparedStatement.setString(1, sku);
            preparedStatement.setInt(2, storeId);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getLong("id");
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return id;
    }

    public Long getOfferIdForAlcohol(Integer storeId) {
        Long id = null;
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "o.id") +
                     " sp JOIN offers o ON sp.id = o.product_id WHERE sp.shipping_category_id = 3 AND o.store_id = ? AND o.published = 1 AND sp.deleted_at IS NULL AND o.deleted_at IS NULL LIMIT 1")) {
            preparedStatement.setInt(1, storeId);
            log.info("Sql get alcohol: {}", preparedStatement);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getLong("id");
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return id;
    }

    public Long getOfferIdForPharma(Integer storeId) {
        Long id = null;
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "o.id") +
                     " sp JOIN offers o ON sp.id = o.product_id WHERE sp.shipping_category_id = 4 AND o.store_id = ? AND o.published = 1 AND sp.deleted_at IS NULL AND o.deleted_at IS NULL")) {
            preparedStatement.setInt(1, storeId);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getLong("id");
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return id;
    }

    public Long getOfferIdByPermalink(String permalink, Integer storeId) {
        Long id = null;
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "o.id") +
                     " sp JOIN offers o ON sp.id = o.product_id WHERE sp.permalink = ? AND o.store_id = ? AND o.published = 1 AND sp.deleted_at IS NULL AND o.deleted_at IS NULL")) {
            preparedStatement.setString(1, permalink);
            preparedStatement.setInt(2, storeId);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getLong("id");
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return id;
    }

    public SpreeProductsEntity getProduct() {
        SpreeProductsEntity spreeProductsEntity = new SpreeProductsEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE deleted_at IS NULL AND permalink NOT LIKE '%1%' AND shipping_category_id != 3 LIMIT 1")) {
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    spreeProductsEntity.setId(resultSet.getLong("id"));
                    spreeProductsEntity.setName(resultSet.getString("name"));
                    spreeProductsEntity.setPermalink(resultSet.getString("permalink"));
                    spreeProductsEntity.setShippingCategoryId(resultSet.getInt("shipping_category_id"));
                    spreeProductsEntity.setBrandId(resultSet.getLong("brand_id"));
                    spreeProductsEntity.setEan(resultSet.getString("ean"));
                    spreeProductsEntity.setSku(resultSet.getString("sku"));
                }
            }

        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return spreeProductsEntity;
    }

    public SpreeProductsEntity getProductBySku(String sku) {
        final var spreeProductsEntity = new SpreeProductsEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE sku = ?")) {
            preparedStatement.setString(1, sku);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    spreeProductsEntity.setId(resultSet.getLong("id"));
                    spreeProductsEntity.setName(resultSet.getString("name"));
                    spreeProductsEntity.setPermalink(resultSet.getString("permalink"));
                    spreeProductsEntity.setShippingCategoryId(resultSet.getInt("shipping_category_id"));
                    spreeProductsEntity.setBrandId(resultSet.getLong("brand_id"));
                    spreeProductsEntity.setEan(resultSet.getString("ean"));
                    spreeProductsEntity.setSku(resultSet.getString("sku"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return spreeProductsEntity;
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
