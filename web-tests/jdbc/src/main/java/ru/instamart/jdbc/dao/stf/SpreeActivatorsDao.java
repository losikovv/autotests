package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeActivatorsEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class SpreeActivatorsDao extends AbstractDao<Long, SpreeActivatorsEntity> {

    public static final SpreeActivatorsDao INSTANCE = new SpreeActivatorsDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_activators";

    @Override
    public Optional<SpreeActivatorsEntity> findById(Long id) {
        SpreeActivatorsEntity spreeActivatorsEntity = null;
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE id = ?")) {
            preparedStatement.setObject(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    spreeActivatorsEntity = new SpreeActivatorsEntity();
                    spreeActivatorsEntity.setId(resultSet.getLong("id"));
                    spreeActivatorsEntity.setName(resultSet.getString("name"));
                    spreeActivatorsEntity.setType(resultSet.getString("type"));
                    spreeActivatorsEntity.setEventName(resultSet.getString("event_name"));
                    spreeActivatorsEntity.setServiceComment(resultSet.getString("service_comment"));
                    spreeActivatorsEntity.setPrefix(resultSet.getString("prefix"));
                    spreeActivatorsEntity.setDescription(resultSet.getString("description"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return Optional.ofNullable(spreeActivatorsEntity);
    }
}
