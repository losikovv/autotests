package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.SpreeBrandsEntity;
import ru.instamart.jdbc.entity.SpreeTaxonsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreeTaxonsDao extends AbstractDao<Long, SpreeTaxonsEntity> {

    public static final SpreeTaxonsDao INSTANCE = new SpreeTaxonsDao();
    private static final String SELECT_SQL = "SELECT %s FROM spree_taxons";
    private final String UPDATE_SQL = "UPDATE spree_taxons";

    public SpreeTaxonsEntity getTaxonByInstamartId(Integer instamartId) {
        SpreeTaxonsEntity taxon = new SpreeTaxonsEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") +
                     " WHERE instamart_id = ?")) {
            preparedStatement.setInt(1, instamartId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                taxon.setId(resultSet.getLong("id"));
                taxon.setParentId(resultSet.getLong("parent_id"));
                taxon.setPosition(resultSet.getInt("position"));
                taxon.setName(resultSet.getString("name"));
                taxon.setPermalink(resultSet.getString("permalink"));
                taxon.setTaxonomyId(resultSet.getLong("taxonomy_id"));
                taxon.setIconFileName(resultSet.getString("icon_file_name"));
                taxon.setIconContentType(resultSet.getString("icon_content_type"));
                taxon.setIconFileSize(resultSet.getInt("icon_file_size"));
                taxon.setKeywords(resultSet.getString("keywords"));
                taxon.setShippingCategoryId(resultSet.getLong("shipping_category_id"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return taxon;
    }

    public void updateTaxonIcon(String iconFileName, String iconContentType, Integer iconFileSize) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_SQL + " SET icon_file_name = ?, icon_content_type = ?, " +
                     "icon_file_size = ?")) {
            preparedStatement.setObject(1, iconFileName);
            preparedStatement.setObject(2, iconContentType);
            preparedStatement.setObject(3, iconFileSize);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
