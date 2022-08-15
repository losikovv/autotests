package ru.instamart.jdbc.dao.stf;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeUsersEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

@Slf4j
public class SpreeUsersDao extends AbstractDao<Long, SpreeUsersEntity> {

    public static final SpreeUsersDao INSTANCE = new SpreeUsersDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_users";
    private final String DELETE_SQL = "DELETE FROM spree_users";

    private static final String INSERT_SQL = "INSERT INTO spree_roles_users(role_id, user_id) VALUES(?, ?)";

    @Override
    public Optional<SpreeUsersEntity> findById(Long id) {
        SpreeUsersEntity user = null;
        try (final var connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             final var preparedStatement = connect.prepareStatement( String.format(SELECT_SQL, "*") + " WHERE id = ?")) {
            preparedStatement.setObject(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new SpreeUsersEntity();
                    user.setId(resultSet.getLong("id"));
                    user.setLogin(resultSet.getString("login"));
                    user.setSpreeApiKey(resultSet.getString("spree_api_key"));
                    user.setFirstname(resultSet.getString("firstname"));
                    user.setLastname(resultSet.getString("lastname"));
                    user.setUuid(resultSet.getString("uuid"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(user);
    }

    public SpreeUsersEntity getUserByEmail(String email) {
        final var user = new SpreeUsersEntity();
        try (final var connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE email = ?")) {
            preparedStatement.setString(1, email);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getLong("id"));
                    user.setLogin(resultSet.getString("login"));
                    user.setSpreeApiKey(resultSet.getString("spree_api_key"));
                    user.setFirstname(resultSet.getString("firstname"));
                    user.setLastname(resultSet.getString("lastname"));
                    user.setUuid(resultSet.getString("uuid"));
                } else return null;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return user;
    }

    public void deleteUserByEmail(String email) {
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE email = ?")) {
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error init ConnectionMySQLManager. Error: {}", e.getMessage());
        }
    }

    public String getUUIDByLogin(String login) {
        try (final var connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "uuid") + " WHERE login = ?")) {
            preparedStatement.setString(1, login);
            try (final var resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getString("uuid");
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return null;
    }

    public void deleteQAUsers() {
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + ", phone_tokens USING spree_users, phone_tokens" +
                     " WHERE spree_users.id = phone_tokens.user_id AND spree_users.email LIKE 'qasession+%' AND spree_users.locked_at IS NOT NULL")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error init ConnectionMySQLManager. Error: {}", e.getMessage());
        }
    }

    public String getEmailByPhone(String phone) {
        try (final var connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "email") + " su JOIN phone_tokens pt ON su.id = pt.user_id WHERE pt.value = ?")) {
            preparedStatement.setString(1, phone);
            try (final var resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getString("email");
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return null;
    }

    public void addRoleToUser(Integer userId, Integer roleId) {
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(INSERT_SQL)) {
            preparedStatement.setInt(1, roleId);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
