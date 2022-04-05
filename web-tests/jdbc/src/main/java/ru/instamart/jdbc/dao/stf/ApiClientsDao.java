package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.ApiClientsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class ApiClientsDao extends AbstractDao<Long, ApiClientsEntity> {

    public static final ApiClientsDao INSTANCE = new ApiClientsDao();
    private final String SELECT_SQL = "SELECT %s FROM api_clients";

    public String getClientTokenByName(String clientName) {
       String token = null;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "act.value") +
                     " ac JOIN api_client_tokens act ON ac.id = act.api_client_id WHERE ac.client_id = ?")) {
            preparedStatement.setString(1, clientName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                token = resultSet.getString("value");
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return token;
    }

    public Long getClientIdByName(String clientName) {
        Long id = null;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "id") + " WHERE client_id = ?")) {
            preparedStatement.setString(1, clientName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                id = resultSet.getLong("id");
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return id;
    }
}
