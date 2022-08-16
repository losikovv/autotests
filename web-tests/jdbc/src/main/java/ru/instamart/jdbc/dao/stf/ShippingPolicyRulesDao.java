package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.ShippingPolicyRulesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class ShippingPolicyRulesDao extends AbstractDao<Long, ShippingPolicyRulesEntity> {

    public static final ShippingPolicyRulesDao INSTANCE = new ShippingPolicyRulesDao();
    private final String SELECT_SQL = "SELECT %s FROM shipping_policy_rules";
    private final String DELETE_SQL = "DELETE FROM shipping_policy_rules";

    @Override
    public Optional<ShippingPolicyRulesEntity> findById(Long id) {
        ShippingPolicyRulesEntity shippingPolicyRulesEntity = null;
        final var sql = String.format(SELECT_SQL, "*") + " WHERE id = ?";
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    shippingPolicyRulesEntity = new ShippingPolicyRulesEntity();
                    shippingPolicyRulesEntity.setId(resultSet.getLong("id"));
                    shippingPolicyRulesEntity.setShippingPolicyId(resultSet.getLong("shipping_policy_id"));
                    shippingPolicyRulesEntity.setType(resultSet.getString("type"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(shippingPolicyRulesEntity);
    }

    public void deleteRulesByShippingPolicyId(Long shippingPolicyId) {
        try (Connection connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE shipping_policy_id = ?")) {
            preparedStatement.setLong(1, shippingPolicyId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}