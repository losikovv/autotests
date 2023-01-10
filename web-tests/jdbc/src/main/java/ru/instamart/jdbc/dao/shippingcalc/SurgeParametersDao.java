package ru.instamart.jdbc.dao.shippingcalc;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.shippingcalc.SurgeParametersEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SurgeParametersDao implements Dao<Integer, SurgeParametersEntity> {

    public static final SurgeParametersDao INSTANCE = new SurgeParametersDao();

    public SurgeParametersEntity getSurgeParametersByRegionIdAndVertical(Integer regionId, Integer vertical) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             final var preparedStatement = connect.prepareStatement(" SELECT * FROM surge_parameters WHERE region_id = ? AND vertical = ? ")) {
            preparedStatement.setInt(1, regionId);
            preparedStatement.setInt(2, vertical);

            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return SurgeParametersEntity.builder()
                            .id(resultSet.getInt("id"))
                            .regionId(resultSet.getInt("region_id"))
                            .vertical(resultSet.getInt("vertical"))
                            .parameters(resultSet.getString("parameters"))
                            .build();
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return null;
    }

    public Integer setSurgeParameters(SurgeParametersEntity surgeParameters) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             final var preparedStatement = connect.prepareStatement(" INSERT INTO surge_parameters (region_id, vertical, parameters) " +
                     " VALUES (?, ?, ?::jsonb) " +
                     " ON CONFLICT (region_id, vertical) DO UPDATE SET parameters = ?::jsonb ",
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, surgeParameters.getRegionId());
            preparedStatement.setInt(2, surgeParameters.getVertical());
            preparedStatement.setString(3, surgeParameters.getParameters());
            preparedStatement.setString(4, surgeParameters.getParameters());
            preparedStatement.executeUpdate();
            try (final var resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShippingCalcManager. Error: " + e.getMessage());
        }
        return 0;
    }

    public boolean deleteSurgeParameters(List<Integer> idList) {
        final var builder = new StringBuilder();
        final var params = builder.append("?,".repeat(idList.size())).deleteCharAt(builder.length() - 1).toString();
        final var deleteSql = " DELETE FROM surge_parameters WHERE id IN (" + params + ") ";

        try (Connection connect = ConnectionManager.getDataSource(Db.PG_SHIPPING_CALC).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(deleteSql)) {
            int index = 1;
            for (Object parameterId : idList) preparedStatement.setObject(index++, parameterId);
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
    public SurgeParametersEntity save(SurgeParametersEntity ticket) {
        return null;
    }

    @Override
    public void update(SurgeParametersEntity ticket) {

    }

    @Override
    public Optional<SurgeParametersEntity> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<SurgeParametersEntity> findAll() {
        return null;
    }
}
