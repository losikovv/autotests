package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.ShippingPoliciesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class ShippingPoliciesDao extends AbstractDao<Long, ShippingPoliciesEntity> {

    public static final ShippingPoliciesDao INSTANCE = new ShippingPoliciesDao();
    private final String SELECT_SQL = "SELECT %s FROM shipping_policies";

    @Override
    public Optional<ShippingPoliciesEntity> findById(Long id) {
        ShippingPoliciesEntity shippingPoliciesEntity = null;
        final var sql = String.format(SELECT_SQL, "*") + " WHERE id = ?";
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    shippingPoliciesEntity = new ShippingPoliciesEntity();
                    shippingPoliciesEntity.setId(resultSet.getLong("id"));
                    shippingPoliciesEntity.setRetailerId(resultSet.getLong("retailer_id"));
                    shippingPoliciesEntity.setTitle(resultSet.getString("title"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(shippingPoliciesEntity);
    }
}