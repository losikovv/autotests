package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeShippingMethodsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;
import ru.instamart.jdbc.util.Transactional;

import java.sql.SQLException;

import static org.testng.Assert.fail;

public class ShippingMethodsCleanDao extends AbstractDao<Long, SpreeShippingMethodsEntity> {

    public static final ShippingMethodsCleanDao INSTANCE = new ShippingMethodsCleanDao();
    private final String SELECT_FROM_SHIPPING_METHODS_SQL = "SELECT smp.* FROM spree_shipping_methods ssm JOIN shipping_method_pricers smp ON smp.shipping_method_id = ssm.id WHERE ssm.id = ";

    private final String DELETE_FROM_CALCULATORS_SQL = "DELETE FROM shipping_method_pricer_calculators WHERE pricer_id = ";
    private final String DELETE_FROM_RULES_SQL = "DELETE FROM shipping_method_pricer_rules WHERE pricer_id = ";
    private final String DELETE_FROM_PRICERS_SQL = "DELETE FROM shipping_method_pricers WHERE shipping_method_id = ";
    private final String DELETE_FROM_SHIPPING_METHODS_SQL = "DELETE FROM spree_shipping_methods WHERE id = ";

    public void deleteShippineMethods(int shippingMethodId) {
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var statement = connect.createStatement();
             final var transactional = new Transactional(connect, false);
             final var resultSet = statement.executeQuery(SELECT_FROM_SHIPPING_METHODS_SQL + shippingMethodId)
        ) {
            while (resultSet.next()) {
                statement.addBatch(DELETE_FROM_CALCULATORS_SQL + resultSet.getInt("id"));
                statement.addBatch(DELETE_FROM_RULES_SQL + resultSet.getInt("id"));
            }
            statement.addBatch(DELETE_FROM_PRICERS_SQL + shippingMethodId);
            statement.addBatch(DELETE_FROM_SHIPPING_METHODS_SQL + shippingMethodId);
            statement.executeBatch();
            transactional.commit();
        } catch (SQLException e) {
            fail("Error ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
