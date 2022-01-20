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

    public Long getIdByEmail(String email) {
        Long id = null;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "id") + " WHERE email = ?")) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getLong("id");
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return id;
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
}
