package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.SpreePagesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SpreePagesDao implements  Dao<Long, SpreePagesEntity> {

    public static final SpreePagesDao INSTANCE = new SpreePagesDao();
    private final String SELECT_SQL = "SELECT * FROM spree_pages";

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public SpreePagesEntity save(SpreePagesEntity ticket) {
        return null;
    }

    @Override
    public void update(SpreePagesEntity ticket) {

    }

    @Override
    public Optional<SpreePagesEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<SpreePagesEntity> findAll() {
        return null;
    }

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
            e.printStackTrace();
        }
        return page;
    }
}
