package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.PhoneTokensEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class PhoneTokensDao extends AbstractDao<Long, PhoneTokensEntity>{

    public static final PhoneTokensDao INSTANCE = new PhoneTokensDao();
    private final String DELETE_SQL = "DELETE FROM phone_tokens";

    public void deleteQAPhones() {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE value LIKE '7990%'")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
