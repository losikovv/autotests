package ru.instamart.jdbc.dao.shippingcalc;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.shippingcalc.RetailersEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;
import static ru.instamart.kraken.util.TimeUtil.getZonedUTCDate;

public class RetailersDao implements Dao<Integer, RetailersEntity> {

    public static final RetailersDao INSTANCE = new RetailersDao();
    private static final String timestamp = getZonedUTCDate();

    public boolean addRetailer(RetailersEntity retailer) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             final var preparedStatement = connect.prepareStatement(" INSERT INTO retailers (id, uuid, vertical, created_at, updated_at) " +
                     " VALUES (?, ?::uuid, ?, ?::timestamp with time zone, ?::timestamp with time zone) "
             + " ON CONFLICT (id) DO UPDATE SET vertical = ? ")) {
            preparedStatement.setInt(1, retailer.getId());
            preparedStatement.setString(2, retailer.getUuid());
            preparedStatement.setInt(3, retailer.getVertical());
            preparedStatement.setString(4, timestamp);
            preparedStatement.setString(5, timestamp);
            preparedStatement.setInt(6, retailer.getVertical());
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public RetailersEntity save(RetailersEntity ticket) {
        return null;
    }

    @Override
    public void update(RetailersEntity ticket) {

    }

    @Override
    public Optional<RetailersEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<RetailersEntity> findAll() {
        return null;
    }
}
