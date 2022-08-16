package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeTaxonomiesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SpreeTaxonomiesDao extends AbstractDao<Long, SpreeTaxonomiesEntity> {

    public static final SpreeTaxonomiesDao INSTANCE = new SpreeTaxonomiesDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_taxonomies";

    public Long getIdByName(String name) {
        Long id = null;
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "id") + " WHERE name = ?")) {
            preparedStatement.setString(1, name);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getLong("id");
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return id;
    }

    @Override
    public Optional<SpreeTaxonomiesEntity> findById(Long id) {
        SpreeTaxonomiesEntity spreeTaxonomiesEntity = null;
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE id = ?")) {
            preparedStatement.setObject(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    spreeTaxonomiesEntity = new SpreeTaxonomiesEntity();
                    spreeTaxonomiesEntity.setId(resultSet.getLong("id"));
                    spreeTaxonomiesEntity.setName(resultSet.getString("name"));
                    spreeTaxonomiesEntity.setPosition(resultSet.getInt("position"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(spreeTaxonomiesEntity);
    }
}
