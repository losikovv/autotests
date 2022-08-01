package ru.instamart.jdbc.dao.stf;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeUsersEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;

import static org.testng.Assert.fail;

@Slf4j
public class UserIdDao extends AbstractDao<Long, SpreeUsersEntity> {

    public static final UserIdDao INSTANCE = new UserIdDao();

    private static final String SQL_SELECT_USER_ID = "SELECT * " +
            "FROM spree_users INNER JOIN phone_tokens ON spree_users.id = phone_tokens.user_id ";

    /**
     * Получение id пользователя по телефону
     *
     * @param phone номер 10 значном формате
     * @return
     */
    public String findUserId(String phone) {
        var sql = SQL_SELECT_USER_ID + "WHERE phone_tokens.value= ? LIMIT 10";

        try (var connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             var preparedStatement = connect.prepareStatement(sql)) {

            preparedStatement.setObject(1, "7" + phone);
            var resultQuery = preparedStatement.executeQuery();
            resultQuery.next();
            return resultQuery.getString("id");
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return null;
    }
}
