package ru.instamart.jdbc.dao.shippingcalc;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.shippingcalc.SurgeThresholdsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SurgeThresholdsDao implements Dao<Integer, SurgeThresholdsEntity> {
    public static final SurgeThresholdsDao INSTANCE = new SurgeThresholdsDao();

    public boolean setSurgeThreshold(SurgeThresholdsEntity surgeThreshold) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             final var preparedStatement = connect.prepareStatement(" INSERT INTO surge_thresholds (region_id, parameters) " +
                     " VALUES (?, ?::jsonb) " +
                     " ON CONFLICT (region_id) DO UPDATE SET parameters = ?::jsonb ")) {
            preparedStatement.setInt(1, surgeThreshold.getRegionId());
            preparedStatement.setString(2, surgeThreshold.getParameters());
            preparedStatement.setString(3, surgeThreshold.getParameters());
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteSurgeThresholds(List<Integer> surgeThresholdsIds) {
        final var builder = new StringBuilder();
        final var params = builder.append("?,".repeat(surgeThresholdsIds.size())).deleteCharAt(builder.length() - 1).toString();
        final var deleteSql = " DELETE FROM surge_thresholds WHERE region_id IN (" + params + ") ";

        try (Connection connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(deleteSql)) {
            int index = 1;
            for (Object surgeThresholdId : surgeThresholdsIds) preparedStatement.setObject(index++, surgeThresholdId);
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
    public SurgeThresholdsEntity save(SurgeThresholdsEntity ticket) {
        return null;
    }

    @Override
    public void update(SurgeThresholdsEntity ticket) {

    }

    @Override
    public Optional<SurgeThresholdsEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<SurgeThresholdsEntity> findAll() {
        return null;
    }
}
