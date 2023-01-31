package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeCreditCardEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreeCreditCardDao extends AbstractDao<Long, SpreeCreditCardEntity> {

    public static final SpreeCreditCardDao INSTANCE = new SpreeCreditCardDao();

    public boolean updateGatewayCustomerProfileId(final String id, final String token){
        final String SQL = "UPDATE spree_credit_cards SET  gateway_customer_profile_id=? WHERE id=?";
        try (Connection connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(SQL)) {
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, token);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return false;
    }
}
