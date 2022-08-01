package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreePaymentMethodsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.fail;

public class SpreePaymentMethodsDao extends AbstractDao<Long, SpreePaymentMethodsEntity> {

    public static final SpreePaymentMethodsDao INSTANCE = new SpreePaymentMethodsDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_payment_methods";

    public List<Long> getPaymentMethodsIds() {
        List<Long> ids = new ArrayList<>();
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "id") + " WHERE deleted_at IS NULL")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ids.add(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return ids;
    }

    public List<Long> getActivePaymentMethodsIds() {
        List<Long> ids = new ArrayList<>();
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "id") + " WHERE active = 1 AND deleted_at IS NULL")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ids.add(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return ids;
    }
}
