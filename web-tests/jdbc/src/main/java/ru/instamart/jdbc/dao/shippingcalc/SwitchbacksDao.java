package ru.instamart.jdbc.dao.shippingcalc;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.shippingcalc.SwitchbacksEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SwitchbacksDao implements Dao<Integer, SwitchbacksEntity> {
    public static final SwitchbacksDao INSTANCE = new SwitchbacksDao();

    public List<SwitchbacksEntity> getSwitchbacks() {
        final var switchbacksResult = new ArrayList<SwitchbacksEntity>();

        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             final var preparedStatement = connect.prepareStatement(" SELECT * FROM switchbacks ORDER BY id")) {
            try (final var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    var switchbacksEntity = SwitchbacksEntity.builder()
                            .id(resultSet.getInt("id"))
                            .startDateTime(resultSet.getString("start_date_time"))
                            .endDateTime(resultSet.getString("end_date_time"))
                            .regionId(resultSet.getInt("region_id"))
                            .vertical(resultSet.getInt("vertical"))
                            .testGroup(resultSet.getString("test_group"))
                            .parameters(resultSet.getString("parameters"))
                            .build();
                    switchbacksResult.add(switchbacksEntity);
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return switchbacksResult;
    }

    public boolean updateSwitchbackState(String endDateTime, Integer switchbackId) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             final var preparedStatement = connect.prepareStatement(" UPDATE switchbacks SET end_date_time = ?::timestamp WHERE id = ? ")) {
            preparedStatement.setObject(1, endDateTime);
            preparedStatement.setInt(2,switchbackId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return false;
    }

    public boolean setSwitchbacks(SwitchbacksEntity switchback) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             final var preparedStatement = connect.prepareStatement(" INSERT INTO switchbacks (start_date_time, end_date_time, region_id, vertical, test_group, parameters) " +
                     " VALUES (?::timestamp with time zone, ?::timestamp with time zone, ?, ?, ?::text, ?::jsonb) ")) {
            preparedStatement.setString(1, switchback.getStartDateTime());
            preparedStatement.setString(2, switchback.getEndDateTime());
            preparedStatement.setInt(3, switchback.getRegionId());
            preparedStatement.setInt(4, switchback.getVertical());
            preparedStatement.setString(5, switchback.getTestGroup());
            preparedStatement.setString(6, switchback.getParameters());
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
    public SwitchbacksEntity save(SwitchbacksEntity ticket) {
        return null;
    }

    @Override
    public void update(SwitchbacksEntity ticket) {

    }

    @Override
    public Optional<SwitchbacksEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<SwitchbacksEntity> findAll() {
        return null;
    }
}
