package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeShippingMethodsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreeShippingMethodsDao extends AbstractDao<Long, SpreeShippingMethodsEntity> {

    public static final SpreeShippingMethodsDao INSTANCE = new SpreeShippingMethodsDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_shipping_methods";

    public Long getShippingMethodId(String kind) {
        Long id = null;
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "id") + " WHERE kind = ? AND deleted_at IS NULL LIMIT 1")) {
            preparedStatement.setString(1, kind);
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
