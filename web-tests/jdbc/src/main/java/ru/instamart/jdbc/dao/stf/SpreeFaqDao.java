package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeFaqEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SpreeFaqDao extends AbstractDao<Long, SpreeFaqEntity> {

    public static final SpreeFaqDao INSTANCE = new SpreeFaqDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_faqs";

    @Override
    public Optional<SpreeFaqEntity> findById(Long id) {
        SpreeFaqEntity spreeFaqEntity = null;
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE id = ?")) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                spreeFaqEntity = new SpreeFaqEntity();
                spreeFaqEntity.setId(resultSet.getLong("id"));
                spreeFaqEntity.setFaqGroupId(resultSet.getLong("faq_group_id"));
                spreeFaqEntity.setPosition(resultSet.getInt("position"));
                spreeFaqEntity.setQuestion(resultSet.getString("question"));
                spreeFaqEntity.setAnswer(resultSet.getString("answer"));
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(spreeFaqEntity);
    }

    public int getCount(Long faqGroupId) {
        int resultCount = 0;
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total") + " WHERE faq_group_id = ?")) {
            preparedStatement.setLong(1, faqGroupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            resultCount = resultSet.getInt("total");
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return resultCount;
    }
}
