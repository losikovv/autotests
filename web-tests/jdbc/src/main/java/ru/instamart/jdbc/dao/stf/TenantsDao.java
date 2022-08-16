package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.TenantsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;

import static org.testng.Assert.fail;

public class TenantsDao extends AbstractDao<Long, TenantsEntity> {

    public static final TenantsDao INSTANCE = new TenantsDao();
    private final String SELECT_SQL = "SELECT %s FROM tenants";

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
