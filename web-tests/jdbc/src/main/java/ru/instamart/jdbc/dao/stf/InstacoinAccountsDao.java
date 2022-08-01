package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.InstacoinAccountsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class InstacoinAccountsDao extends AbstractDao<Long, InstacoinAccountsEntity> {

    public static final InstacoinAccountsDao INSTANCE = new InstacoinAccountsDao();

    public void updatePromotionCode(Long promotionCodeId, Long userId) {
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement("UPDATE instacoin_accounts SET promotion_code_id = ? WHERE user_id = ?")) {
            preparedStatement.setObject(1, promotionCodeId);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
