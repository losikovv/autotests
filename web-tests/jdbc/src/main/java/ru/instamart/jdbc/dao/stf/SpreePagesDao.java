package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreePagesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreePagesDao extends AbstractDao<Long, SpreePagesEntity> {

    public static final SpreePagesDao INSTANCE = new SpreePagesDao();
    private final String SELECT_SQL = "SELECT * FROM spree_pages";
    private final String DELETE_SQL = "DELETE FROM spree_pages";

    public SpreePagesEntity getPageBySlug(String pageSlug) {
        SpreePagesEntity page = new SpreePagesEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(SELECT_SQL +
                     " WHERE slug = ?")) {
            preparedStatement.setString(1, pageSlug);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                page.setId(resultSet.getLong("id"));
                page.setTitle(resultSet.getString("title"));
                page.setBody(resultSet.getString("body"));
                page.setMetaTitle(resultSet.getString("meta_title"));
                page.setMetaDescription(resultSet.getString("meta_description"));
                page.setMetaKeywords(resultSet.getString("meta_keywords"));
                page.setForeignLink(resultSet.getString("foreign_link"));
                page.setLayout(resultSet.getString("layout"));
                page.setPosition(resultSet.getInt("position"));
                page.setVisible(resultSet.getInt("visible"));
                page.setShowInFooter(resultSet.getInt("show_in_footer"));
                page.setShowInHeader(resultSet.getInt("show_in_header"));
                page.setShowInSidebar(resultSet.getInt("show_in_sidebar"));
                page.setRenderLayoutAsPartial(resultSet.getInt("render_layout_as_partial"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return page;
    }

    public void deletePageBySlug(String slug) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(DELETE_SQL + " WHERE slug LIKE ?")) {
            preparedStatement.setString(1, slug + "%");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
