package ru.instamart.jdbc.dao.shippingcalc;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.shippingcalc.SwitchbackExperimentsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SwitchbackExperimentsDao implements Dao<Integer, SwitchbackExperimentsEntity> {
    public static final SwitchbackExperimentsDao INSTANCE = new SwitchbackExperimentsDao();

    public boolean setSwitchbackExperiments(SwitchbackExperimentsEntity switchbackExperiment) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             final var preparedStatement = connect.prepareStatement(" INSERT INTO switchback_experiments (start_date_time, end_date_time, region_id, \"group\", parameters) " +
                     " VALUES (?::timestamp with time zone, ?::timestamp with time zone, ?, ?::text, ?::jsonb) ")) {
            preparedStatement.setString(1, switchbackExperiment.getStartDateTime());
            preparedStatement.setString(2, switchbackExperiment.getEndDateTime());
            preparedStatement.setInt(3, switchbackExperiment.getRegionId());
            preparedStatement.setString(4, switchbackExperiment.getGroup());
            preparedStatement.setString(5, switchbackExperiment.getParameters());
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
    public SwitchbackExperimentsEntity save(SwitchbackExperimentsEntity ticket) {
        return null;
    }

    @Override
    public void update(SwitchbackExperimentsEntity ticket) {

    }

    @Override
    public Optional<SwitchbackExperimentsEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<SwitchbackExperimentsEntity> findAll() {
        return null;
    }
}
