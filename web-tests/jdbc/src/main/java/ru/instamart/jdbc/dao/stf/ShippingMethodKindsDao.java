package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.ShippingMethodKindsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;

import static org.testng.Assert.fail;

public class ShippingMethodKindsDao extends AbstractDao<Long, ShippingMethodKindsEntity> {

    public static final ShippingMethodKindsDao INSTANCE = new ShippingMethodKindsDao();
    private final String SELECT_SQL = "SELECT %s FROM shipping_method_kinds";

    public int getCount() {
        int resultCount = 0;
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total"));
             final var resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                resultCount = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return resultCount;
    }
}


