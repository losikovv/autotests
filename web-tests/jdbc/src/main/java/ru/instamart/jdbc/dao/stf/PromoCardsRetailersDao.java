package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.PromoCardsRetailersEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;

import static org.testng.Assert.fail;

public class PromoCardsRetailersDao extends AbstractDao<Long, PromoCardsRetailersEntity> {

    public static final PromoCardsRetailersDao INSTANCE = new PromoCardsRetailersDao();
    private final String SELECT_SQL = "SELECT %s FROM promo_cards_retailers";

    public int getCountByRetailerId(Long retailerId) {
        int resultCount = 0;
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total") + " pcr JOIN promo_cards pc ON pcr.promo_card_id  = pc.id" +
                     " WHERE pcr.retailer_id = ?")) {
            preparedStatement.setLong(1, retailerId);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    resultCount = resultSet.getInt("total");
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return resultCount;
    }
}
