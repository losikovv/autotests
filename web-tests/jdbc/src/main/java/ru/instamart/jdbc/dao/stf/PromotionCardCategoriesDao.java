package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.PromotionCardCategoriesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class PromotionCardCategoriesDao extends AbstractDao<Long, PromotionCardCategoriesEntity> {

    public static final PromotionCardCategoriesDao INSTANCE = new PromotionCardCategoriesDao();
    private final String SELECT_SQL = "SELECT %s FROM promotion_card_categories";

    public int getCount() {
        int resultCount = 0;
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total"))) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            resultCount = resultSet.getInt("total");
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return resultCount;
    }
}
