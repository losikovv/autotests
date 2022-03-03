package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.PromotionCardCategoriesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

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
        try (Connection connect = ConnectionMySQLManager.get();
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
