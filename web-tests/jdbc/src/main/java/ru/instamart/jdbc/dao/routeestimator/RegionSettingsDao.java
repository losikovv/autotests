package ru.instamart.jdbc.dao.routeestimator;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.routeestimator.RegionSettingsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class RegionSettingsDao implements Dao<Integer, RegionSettingsEntity> {
    public static final RegionSettingsDao INSTANCE = new RegionSettingsDao();
    private final String SELECT_SQL = "SELECT %s FROM region_settings ";

    public RegionSettingsEntity getSettings(Integer regionId){
        RegionSettingsEntity regionSettingsEntity = new RegionSettingsEntity();
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ROUTE_ESTIMATOR).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE region_id = ? ")) {
            preparedStatement.setInt(1, regionId);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    regionSettingsEntity.setRegionId(resultSet.getInt("region_id"));
                    regionSettingsEntity.setBicycleCorrectionFactor(resultSet.getDouble("bicycle_correction_factor"));
                    regionSettingsEntity.setBicycleIncreasingFactorSec(resultSet.getInt("bicycle_increasing_factor_sec"));
                    regionSettingsEntity.setBicycleMinimumSegmentLengthSec(resultSet.getInt("bicycle_minimum_segment_length_sec"));
                    regionSettingsEntity.setAutoCorrectionFactor(resultSet.getDouble("auto_correction_factor"));
                    regionSettingsEntity.setAutoIncreasingFactorSec(resultSet.getInt("auto_increasing_factor_sec"));
                    regionSettingsEntity.setAutoMinimumSegmentLengthSec(resultSet.getInt("auto_minimum_segment_length_sec"));
                    regionSettingsEntity.setPedestrianCorrectionFactor(resultSet.getDouble("pedestrian_correction_factor"));
                    regionSettingsEntity.setPedestrianIncreasingFactorSec(resultSet.getInt("pedestrian_increasing_factor_sec"));
                    regionSettingsEntity.setPedestrianMinimumSegmentLengthSec(resultSet.getInt("pedestrian_minimum_segment_length_sec"));
                    regionSettingsEntity.setCreatedAt(resultSet.getString("created_at"));
                    regionSettingsEntity.setUpdatedAt(resultSet.getString("updated_at"));
                } else return null;
        }
    } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return regionSettingsEntity;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public RegionSettingsEntity save(RegionSettingsEntity ticket) {
        return null;
    }

    @Override
    public void update(RegionSettingsEntity ticket) {

    }

    @Override
    public Optional<RegionSettingsEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<RegionSettingsEntity> findAll() {
        return null;
    }


}

