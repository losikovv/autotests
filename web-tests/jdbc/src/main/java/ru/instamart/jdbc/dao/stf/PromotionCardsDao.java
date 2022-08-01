package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.PromotionCardsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class PromotionCardsDao extends AbstractDao<Long, PromotionCardsEntity> {

    public static final PromotionCardsDao INSTANCE = new PromotionCardsDao();
    private final String SELECT_SQL = "SELECT %s FROM promotion_cards";

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

    @Override
    public Optional<PromotionCardsEntity> findById(Long id) {
        PromotionCardsEntity promotionCardsEntity = null;
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement( String.format(SELECT_SQL, "*") + " WHERE id = ?")) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                promotionCardsEntity = new PromotionCardsEntity();
                promotionCardsEntity.setId(resultSet.getLong("id"));
                promotionCardsEntity.setTitle(resultSet.getString("title"));
                promotionCardsEntity.setShortDescription(resultSet.getString("short_description"));
                promotionCardsEntity.setFullDescription(resultSet.getString("full_description"));
                promotionCardsEntity.setBackgroundColor(resultSet.getString("background_color"));
                promotionCardsEntity.setMessageColor(resultSet.getString("message_color"));
                promotionCardsEntity.setPromotionId(resultSet.getLong("promotion_id"));
                promotionCardsEntity.setPublished(resultSet.getInt("published"));
                promotionCardsEntity.setType(resultSet.getString("type"));
                promotionCardsEntity.setCategoryId(resultSet.getLong("category_id"));
                promotionCardsEntity.setPosition(resultSet.getInt("position"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(promotionCardsEntity);
    }
}
