package ru.instamart.jdbc.dao;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UserIdDao implements Dao {

    public static final UserIdDao INSTANCE = new UserIdDao();

    private static final String SQL_SELECT_USER_ID = "SELECT * " +
            "FROM spree_users INNER JOIN phone_tokens ON spree_users.id = phone_tokens.user_id ";
    private List<String> result = new ArrayList<>();

    @Override
    public boolean delete(Object id) {
        return false;
    }

    @Override
    public Object save(Object ticket) {
        return null;
    }

    @Override
    public void update(Object ticket) {

    }

    @Override
    public Optional findById(Object id) {
        return Optional.empty();
    }

    @Override
    public List findAll() {
        return null;
    }

    /**
     * Получение id пользователя по телефону
     * @param phone номер 10 значном формате
     * @return
     */
    public List<String> findUserId(String phone) {
        var sql = SQL_SELECT_USER_ID + "WHERE phone_tokens.value= ? LIMIT 10";

        try (var connect = ConnectionMySQLManager.get();
             var preparedStatement = connect.prepareStatement(sql)) {

            preparedStatement.setObject(1, "7" + phone);
            var resultQuery = preparedStatement.executeQuery();
            while (resultQuery.next()) {
                result.add(resultQuery.getString("id"));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
