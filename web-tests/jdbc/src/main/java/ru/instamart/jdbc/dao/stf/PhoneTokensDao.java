package ru.instamart.jdbc.dao.stf;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.PhoneTokensEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

@Slf4j
public class PhoneTokensDao extends AbstractDao<Long, PhoneTokensEntity> {

    public static final PhoneTokensDao INSTANCE = new PhoneTokensDao();
    private final String DELETE_SQL = "DELETE FROM phone_tokens";
    private final String SELECT_SQL = "SELECT %s FROM phone_tokens";

    public void deleteQAPhones() {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE value LIKE '7990%'")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error init ConnectionMySQLManager. Error: {}", e.getMessage());
        }
    }

    public PhoneTokensEntity getByPhoneValue(String phone) {
        PhoneTokensEntity phoneTokensEntity = new PhoneTokensEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, '*') + " WHERE value LIKE ?")) {
            preparedStatement.setString(1, '%' + phone);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            phoneTokensEntity.setUserId(resultSet.getLong("user_id"));
            phoneTokensEntity.setConfirmed(resultSet.getInt("confirmed"));
            phoneTokensEntity.setConfirmationCode(resultSet.getInt("confirmation_code"));
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return phoneTokensEntity;
    }
}
