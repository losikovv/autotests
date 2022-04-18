package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.SpreeAdjustmentsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SpreeAdjustmentsDao extends AbstractDao<Long, SpreeAdjustmentsEntity> {

    public static final SpreeAdjustmentsDao INSTANCE = new SpreeAdjustmentsDao();
    private static final String SELECT_SQL = "SELECT %s FROM spree_adjustments";

    @Override
    public Optional<SpreeAdjustmentsEntity> findById(Long id) {
        SpreeAdjustmentsEntity adjustment = null;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                adjustment = new SpreeAdjustmentsEntity();
                adjustment.setId(resultSet.getLong("id"));
                adjustment.setSourceType(resultSet.getString("source_type"));
                adjustment.setAdjustableType(resultSet.getString("adjustable_type"));
                adjustment.setOriginatorType(resultSet.getString("originator_type"));
                adjustment.setAmount(resultSet.getDouble("amount"));
                adjustment.setLabel(resultSet.getString("label"));
                adjustment.setState(resultSet.getString("state"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(adjustment);
    }
}
