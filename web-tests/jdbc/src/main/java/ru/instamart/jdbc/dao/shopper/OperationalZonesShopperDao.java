package ru.instamart.jdbc.dao.shopper;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.OperationalZonesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class OperationalZonesShopperDao extends AbstractDao<Long, OperationalZonesEntity> {
    public static final OperationalZonesShopperDao INSTANCE = new OperationalZonesShopperDao();
    private final String DELETE_SQL = "DELETE FROM operational_zones ";
    //Локально может падать, тк подключение использует RO юзера

    public void deleteZoneByName(String zoneName) {
        try (Connection connect = ConnectionManager.getConnection(Db.PG_SHP);
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + "WHERE name = ?")) {
            preparedStatement.setString(1, zoneName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLManager. Error: " + e.getMessage());
        }
    }

    public void deleteZoneByNameLike(String zoneName) {
        try (Connection connect = ConnectionManager.getConnection(Db.PG_SHP);
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE name LIKE ?")) {
            preparedStatement.setString(1, zoneName + "%");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}

