package ru.instamart.jdbc.dao.surgelevel;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.surgelevel.ConfigInheritanceEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class ConfigInheritanceDao extends AbstractDao<String, ConfigInheritanceEntity> {

    public static final ConfigInheritanceDao INSTANCE = new ConfigInheritanceDao();
    private final String INSERT_SQL = "INSERT INTO config_inheritance ";

    public boolean addConfigInheritance(String inheritorId, String inheritedId, Integer priority){
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_SURGE_LEVEL).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(INSERT_SQL + " (inheritor_id, inherited_id, priority) " +
                     " VALUES (?::uuid, ?::uuid, ?) ")) {
            preparedStatement.setString(1, inheritorId);
            preparedStatement.setString(2, inheritedId);
            preparedStatement.setInt(3, priority);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLSurgelevelManager. Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Optional<ConfigInheritanceEntity> findById(String id) {
        return Optional.empty();
    }
}
