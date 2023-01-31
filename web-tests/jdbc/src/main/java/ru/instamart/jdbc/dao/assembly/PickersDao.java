package ru.instamart.jdbc.dao.assembly;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.assembly.PickersEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;

import static org.testng.Assert.fail;

public class PickersDao extends AbstractDao<Long, PickersDao> {

    public static final PickersDao INSTANCE = new PickersDao();
    private final String SELECT_SQL = "SELECT * FROM public.pickers";

    public PickersEntity getByPhone(String phone) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ASSEMBLY).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE phone_number = ?")) {
            preparedStatement.setString(1, phone);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    final var pickersEntity = new PickersEntity();
                    pickersEntity.setId(resultSet.getLong("_id"));
                    pickersEntity.setUuidId(resultSet.getString("id"));
                    pickersEntity.setCreatedAt(resultSet.getTimestamp("created_at"));
                    pickersEntity.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    pickersEntity.setActive(resultSet.getBoolean("active"));
                    pickersEntity.setShiftId(resultSet.getInt("shp_id"));
                    pickersEntity.setStoreId(resultSet.getString("store_id"));
                    pickersEntity.setStoreShpId(resultSet.getLong("store_shp_id"));
                    pickersEntity.setLastShpEventDate(resultSet.getTimestamp("last_shp_event_date"));
                    pickersEntity.setLockedAt(resultSet.getTimestamp("locked_at"));
                    pickersEntity.setDispatchOn(resultSet.getBoolean("dispatch_on"));
                    pickersEntity.setShiftsEventCreatedAt(resultSet.getTimestamp("shifts_event_created_at"));
                    pickersEntity.setReadyAt(resultSet.getTimestamp("ready_at"));
                    pickersEntity.setShipmentIds(resultSet.getString("shipment_ids"));
                    pickersEntity.setRole(resultSet.getString("role"));
                    pickersEntity.setEmploymentType(resultSet.getLong("employment_type"));
                    pickersEntity.setShiftId(resultSet.getInt("shift_id"));
                    pickersEntity.setFullName(resultSet.getString("full_name"));
                    pickersEntity.setPhoneNumber(resultSet.getString("phone_number"));
                    pickersEntity.setHasPausedShipments(resultSet.getBoolean("has_paused_shipments"));
                    pickersEntity.setVersion(resultSet.getLong("version"));
                    pickersEntity.setFreeFor(resultSet.getLong("free_for"));
                    pickersEntity.setHasOffer(resultSet.getString("has_offer"));
                    pickersEntity.setOfferId(resultSet.getString("offer_id"));
                    pickersEntity.setReadyToDispatchAt(resultSet.getTimestamp("ready_to_dispatch_at"));
                    pickersEntity.setSyncedWithShpAt(resultSet.getTimestamp("synced_with_shp_at"));
                    return pickersEntity;
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLCandidateManager. Error: " + e.getMessage());
        }
        return null;
    }
}
