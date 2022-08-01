package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.ShipmentReviewIssuesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class ShipmentReviewIssuesDao extends AbstractDao<Long, ShipmentReviewIssuesEntity> {

    public static final ShipmentReviewIssuesDao INSTANCE = new ShipmentReviewIssuesDao();
    private final String SELECT_SQL = "SELECT %s FROM shipment_review_issues";

    public int getCount() {
        int resultCount = 0;
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total") +
                     " ri LEFT JOIN shipment_review_issue_payment_methods pm ON ri.id = pm.shipment_review_issue_id WHERE pm.id IS NULL AND ri.active = 1")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            resultCount = resultSet.getInt("total");
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return resultCount;
    }
}
