package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.OrderCompensationsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;
import ru.instamart.kraken.util.ThreadUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.testng.Assert.fail;

public class OrderCompensationsDao extends AbstractDao<Long, OrderCompensationsEntity> {

    public static final OrderCompensationsDao INSTANCE = new OrderCompensationsDao();
    private static final String SELECT_SQL = "SELECT %s FROM order_compensations";
    private static final String UPDATE_SQL = "UPDATE order_compensations";

    @Override
    public Optional<OrderCompensationsEntity> findById(Long id) {
        OrderCompensationsEntity orderCompensationsEntity = null;
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement( String.format(SELECT_SQL, "*") + " WHERE id = ?")) {
            preparedStatement.setObject(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    orderCompensationsEntity = new OrderCompensationsEntity();
                    orderCompensationsEntity.setId(resultSet.getLong("id"));
                    orderCompensationsEntity.setEmail(resultSet.getString("email"));
                    orderCompensationsEntity.setEmailBody(resultSet.getString("email_body"));
                    orderCompensationsEntity.setReason(resultSet.getLong("reason"));
                    orderCompensationsEntity.setAmount(resultSet.getDouble("amount"));
                    orderCompensationsEntity.setPromoType(resultSet.getLong("promo_type"));
                    orderCompensationsEntity.setState(resultSet.getInt("state"));
                    orderCompensationsEntity.setCustomerId(resultSet.getLong("customer_id"));
                    orderCompensationsEntity.setCreatorId(resultSet.getLong("creator_id"));
                    orderCompensationsEntity.setOrderId(resultSet.getLong("order_id"));
                    orderCompensationsEntity.setPromotionId(resultSet.getLong("promotion_id"));
                    orderCompensationsEntity.setPromotionCodeId(resultSet.getLong("promotion_code_id"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(orderCompensationsEntity);
    }

    public void updateState(Long orderCompensationId, int state) {
        AtomicInteger count = new AtomicInteger(0);
        while (count.get() < 30) {
            boolean result = updateStateData(orderCompensationId, state);
            if (result)
                break;
            ThreadUtil.simplyAwait(1);
            count.getAndIncrement();
        }
    }

    private boolean updateStateData(Long orderCompensationId, int state) {
        try (Connection connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_SQL + " SET state = ? WHERE id = ?")) {
            preparedStatement.setInt(1, state);
            preparedStatement.setLong(2, orderCompensationId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return false;
    }
}
