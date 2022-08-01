package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.ImageDraftsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class ImagesDraftsDao extends AbstractDao<Long, ImageDraftsEntity> {

    public static final ImagesDraftsDao INSTANCE = new ImagesDraftsDao();
    private static final String SELECT_SQL = "SELECT %s FROM image_drafts";
    private final String DELETE_SQL = "DELETE FROM image_drafts";

    public int getCount(String fileName) {
        int resultCount = 0;
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total") + " WHERE image_file_name LIKE ?")) {
            preparedStatement.setString(1, String.format("%s%%", fileName));
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            resultCount = resultSet.getInt("total");
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return resultCount;
    }

    public void deleteImagesByName(String fileName) {
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE image_file_name LIKE ?")) {
            preparedStatement.setString(1, String.format("%s%%", fileName));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
