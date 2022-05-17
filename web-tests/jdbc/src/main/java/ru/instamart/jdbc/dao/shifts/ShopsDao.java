package ru.instamart.jdbc.dao.shifts;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.shifts.ShopsEntity;
import ru.instamart.jdbc.util.dispatch.ConnectionPgSQLShiftsManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.fail;

public class ShopsDao extends AbstractDao<Long, ShopsEntity> {

    public static final ShopsDao INSTANCE = new ShopsDao();
    private final String SELECT_ORIGINAL_ID_DESK = "SELECT uuid, original_id FROM public.shops ORDER BY original_id DESC LIMIT 1;";

    public List<ShopsEntity> getOriginalId() {
        List<ShopsEntity> shopsResult = new ArrayList<>();
        try (Connection connect = ConnectionPgSQLShiftsManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(SELECT_ORIGINAL_ID_DESK)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var shopsEntity = new ShopsEntity();
                shopsEntity.setUuid(resultSet.getString("uuid"));
                shopsEntity.setOriginalId(resultSet.getInt("original_id"));
                shopsResult.add(shopsEntity);
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLShiftsManager. Error: " + e.getMessage());
        }
        return shopsResult;
    }
}
