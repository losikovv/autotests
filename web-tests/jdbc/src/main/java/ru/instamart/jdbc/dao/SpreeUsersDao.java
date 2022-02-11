package ru.instamart.jdbc.dao;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.jdbc.entity.SpreeUsersEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

@Slf4j
public class SpreeUsersDao extends AbstractDao<Long, SpreeUsersEntity> {

    public static final SpreeUsersDao INSTANCE = new SpreeUsersDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_users";
    private final String DELETE_SQL = "DELETE FROM spree_users";

    public SpreeUsersEntity getUserByEmail(String email) {
        SpreeUsersEntity user = new SpreeUsersEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE email = ?")) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setLogin(resultSet.getString("login"));
                user.setSpreeApiKey(resultSet.getString("spree_api_key"));
                user.setFirstname(resultSet.getString("firstname"));
                user.setLastname(resultSet.getString("lastname"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return user;
    }

    public void deleteUserByEmail(String email) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE email = ?")) {
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error init ConnectionMySQLManager. Error: {}", e.getMessage());
        }
    }

    public String getEmailByLogin(String login) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "email") + " WHERE login = ?")) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("email");
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return null;
    }

    public void deleteQAUsers() {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + ", phone_tokens USING spree_users, phone_tokens" +
                     " WHERE spree_users.id = phone_tokens.user_id AND spree_users.email LIKE 'qasession+%' AND spree_users.locked_at IS NOT NULL")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error init ConnectionMySQLManager. Error: {}", e.getMessage());
        }
    }

    public String getEmailByPhone(String phone) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "email") + " su JOIN phone_tokens pt ON su.id = pt.user_id WHERE pt.value = ?")) {
            preparedStatement.setString(1, phone);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("email");
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return null;
    }
}
