package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.InstacoinAccountsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class InstacoinAccountsDao extends AbstractDao<Long, InstacoinAccountsEntity> {

    public static final InstacoinAccountsDao INSTANCE = new InstacoinAccountsDao();

    public void updatePromotionCode(Long promotionCodeId, Long userId) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement("UPDATE instacoin_accounts SET promotion_code_id = ? WHERE user_id = ?")) {
            preparedStatement.setObject(1, promotionCodeId);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
