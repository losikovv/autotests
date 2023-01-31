package ru.instamart.jdbc.dao.assembly;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.assembly.AssemblyEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;

import static org.testng.Assert.fail;

public class AssemblyDao extends AbstractDao<Long, AssemblyEntity> {

    public static final AssemblyDao INSTANCE = new AssemblyDao();

    public AssemblyEntity getByShipmentNumber(final String shipmentNumber) {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ASSEMBLY).getConnection();
             final var preparedStatement = connect.prepareStatement("SELECT * FROM public.assembly_requests WHERE shipment_number = ?")) {
            preparedStatement.setString(1, shipmentNumber);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    final var assemblyEntity = new AssemblyEntity();
                    assemblyEntity.setPrId(resultSet.getLong("_id"));
                    assemblyEntity.setId(resultSet.getString("id"));
                    assemblyEntity.setState(resultSet.getLong("state"));
                    assemblyEntity.setCreatedAt(resultSet.getTimestamp("created_at"));
                    assemblyEntity.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    assemblyEntity.setEndedAt(resultSet.getTimestamp("ended_at"));
                    assemblyEntity.setCanceledAt(resultSet.getTimestamp("canceled_at"));
                    assemblyEntity.setShipmentShpId(resultSet.getInt("shipment_shp_id"));
                    assemblyEntity.setShipmentId(resultSet.getString("shipment_id"));
                    assemblyEntity.setShipmentNumber(resultSet.getString("shipment_number"));
                    assemblyEntity.setStoreId(resultSet.getString("store_id"));
                    assemblyEntity.setIsFirstOrder(resultSet.getString("is_first_order"));
                    assemblyEntity.setIsHeavy(resultSet.getString("is_heavy"));
                    assemblyEntity.setThermalBagNeeded(resultSet.getString("thermal_bag_needed"));
                    assemblyEntity.setWeight(resultSet.getDouble("weight"));
                    assemblyEntity.setItemsCount(resultSet.getLong("items_count"));
                    assemblyEntity.setDeadlineAt(resultSet.getTimestamp("deadline_at"));
                    assemblyEntity.setStartedAt(resultSet.getTimestamp("started_at"));
                    assemblyEntity.setLockedAt(resultSet.getTimestamp("locked_at"));
                    assemblyEntity.setDispatchAt(resultSet.getTimestamp("dispatch_at"));
                    assemblyEntity.setStartAfter(resultSet.getTimestamp("start_after"));
                    assemblyEntity.setPlannedCollectionDuration(resultSet.getInt("planned_collection_duration"));
                    assemblyEntity.setUserStoreBasket(resultSet.getString("user_store_basket"));
                    assemblyEntity.setSlotStart(resultSet.getTimestamp("slot_start"));
                    return assemblyEntity;
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLCandidateManager. Error: " + e.getMessage());
        }
        return null;
    }
}
