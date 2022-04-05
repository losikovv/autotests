package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.ShippingPoliciesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class ShippingPoliciesDao extends AbstractDao<Long, ShippingPoliciesEntity> {

    public static final ShippingPoliciesDao INSTANCE = new ShippingPoliciesDao();
    private final String SELECT_SQL = "SELECT %s FROM shipping_policies";

    @Override
    public Optional<ShippingPoliciesEntity> findById(Long id) {
        ShippingPoliciesEntity shippingPoliciesEntity = null;
        var sql = String.format(SELECT_SQL, "*") + " WHERE id = ?";
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                shippingPoliciesEntity = new ShippingPoliciesEntity();
                shippingPoliciesEntity.setId(resultSet.getLong("id"));
                shippingPoliciesEntity.setRetailerId(resultSet.getLong("retailer_id"));
                shippingPoliciesEntity.setTitle(resultSet.getString("title"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(shippingPoliciesEntity);
    }
}