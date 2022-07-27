package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.ApiClientHooksEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;


public class ApiClientHooksDao extends AbstractDao<Long, ApiClientHooksEntity> {
    public static final ApiClientHooksDao INSTANCE = new ApiClientHooksDao();

    private final String UPDATE_SQL = "UPDATE api_client_hooks SET url='%s' WHERE api_client_id=?";

    public Boolean updateUrlHook(final Long api_client_id, final String url) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(UPDATE_SQL, url))) {
            preparedStatement.setLong(1, api_client_id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return false;
    }
}
