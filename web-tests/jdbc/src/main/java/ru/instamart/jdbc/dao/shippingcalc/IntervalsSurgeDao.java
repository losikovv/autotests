package ru.instamart.jdbc.dao.shippingcalc;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.shippingcalc.IntervalsSurgeEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class IntervalsSurgeDao implements Dao<Integer, IntervalsSurgeEntity> {
    public static final IntervalsSurgeDao INSTANCE = new IntervalsSurgeDao();
    private final String SELECT_SQL = "SELECT %s FROM intervals_surge ";
    private final String INSERT_SQL = "INSERT INTO intervals_surge ";

    public List<IntervalsSurgeEntity> getIntervals() {
        List<IntervalsSurgeEntity> intervalsResult = new ArrayList<>();
        try (Connection connect = ConnectionManager.getConnection(Db.PG_SHIPPING_CALC);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*"))) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var intervalSurgeEntity = new IntervalsSurgeEntity();
                intervalSurgeEntity.setLeftBoundary(resultSet.getInt("left_boundary"));
                intervalSurgeEntity.setRightBoundary(resultSet.getInt("right_boundary"));
                intervalSurgeEntity.setPriceAddition(resultSet.getInt("price_addition"));
                intervalSurgeEntity.setPercentAddition(resultSet.getInt("percent_addition"));
                intervalsResult.add(intervalSurgeEntity);
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return intervalsResult;
    }

    public boolean setIntervals(List<IntervalsSurgeEntity> intervalsList) {
        try (Connection connect = ConnectionManager.getConnection(Db.PG_SHIPPING_CALC);
             PreparedStatement preparedStatement = connect.prepareStatement(INSERT_SQL + " (left_boundary, right_boundary, price_addition, percent_addition) " +
                     " VALUES (?, ?, ?, ?) ")) {
            int result = 0;
            for (IntervalsSurgeEntity intervalsSurgeEntity : intervalsList) {
                preparedStatement.setInt(1, intervalsSurgeEntity.getLeftBoundary());
                preparedStatement.setInt(2, intervalsSurgeEntity.getRightBoundary());
                preparedStatement.setInt(3, intervalsSurgeEntity.getPriceAddition());
                preparedStatement.setInt(4, intervalsSurgeEntity.getPercentAddition());
                preparedStatement.executeUpdate();
                result++;
            }
            if (result == intervalsList.size()) {
                return true;
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return false;
    }

    public void clearIntervals() {
        try (Connection connect = ConnectionManager.getConnection(Db.PG_SHIPPING_CALC);
             PreparedStatement preparedStatement = connect.prepareStatement("TRUNCATE TABLE intervals_surge")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public IntervalsSurgeEntity save(IntervalsSurgeEntity ticket) {
        return null;
    }

    @Override
    public void update(IntervalsSurgeEntity ticket) {

    }

    @Override
    public Optional<IntervalsSurgeEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<IntervalsSurgeEntity> findAll() {
        return null;
    }
}
