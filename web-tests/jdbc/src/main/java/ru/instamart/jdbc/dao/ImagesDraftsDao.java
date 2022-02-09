package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.ImageDraftsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

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
        try (Connection connect = ConnectionMySQLManager.get();
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
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE image_file_name LIKE ?")) {
            preparedStatement.setString(1, String.format("%s%%", fileName));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
