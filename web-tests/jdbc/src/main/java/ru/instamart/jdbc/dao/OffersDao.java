package ru.instamart.jdbc.dao;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.jdbc.entity.OffersEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.SQLException;

@Slf4j
public final class OffersDao extends AbstractDao<Long, OffersEntity> {

    public static final OffersDao INSTANCE = new OffersDao();

    private static final String SELECT_SOLD_PRODUCT = "SELECT id FROM sbermarket_staging_kraken.offers WHERE stock = 0 AND published = 0 LIMIT 1";

    public int getSoldProduct() {
        try (final var connect = ConnectionMySQLManager.get();
             final var preparedStatement = connect.prepareStatement(SELECT_SOLD_PRODUCT);
             final var resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt("id");
        } catch (SQLException e) {
            log.error("FATAL: Can't obtain id");
        }
        return 0;
    }
}
