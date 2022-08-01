package ru.instamart.jdbc.dao.stf;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.OffersEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;
import ru.instamart.kraken.util.TimeUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

@Slf4j
public final class StoreLabelsDao extends AbstractDao<Long, OffersEntity> {

    public static final StoreLabelsDao INSTANCE = new StoreLabelsDao();

    private static final String INSERT_SQL = "INSERT INTO " +
            "store_label_items(store_label_id, store_id, created_at, updated_at, tenant_id) " +
            "VALUES( ?, ?, ?, ?, ?);";
    private static final String DELETE = "DELETE FROM store_label_items";

    public void addStoreLabelToStore(Integer store_label_id, Integer store_id, String tenant_id) {
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(INSERT_SQL)) {
            preparedStatement.setInt(1, store_label_id);
            preparedStatement.setInt(2, store_id);
            preparedStatement.setString(3, TimeUtil.getDbDate());
            preparedStatement.setString(4, TimeUtil.getDbDate());
            preparedStatement.setString(5, tenant_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }

    public void deleteStoreLabelLink(Integer storeLabelId, Integer storeId) {
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE + " WHERE store_label_id = ? AND store_id = ?")) {
            preparedStatement.setInt(1, storeLabelId);
            preparedStatement.setInt(2, storeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
