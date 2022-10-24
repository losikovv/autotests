package ru.instamart.jdbc.dao.surgelevel;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.surgelevel.RegionEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class RegionDao implements Dao<Long, RegionEntity> {

    public static final RegionDao INSTANCE = new RegionDao();

    @Override
    public boolean delete(Long regionId) {
        try (Connection connect = ConnectionManager.getDataSource(Db.PG_SURGE_LEVEL).getConnection();
             PreparedStatement preparedStatement = connect.prepareStatement(" DELETE FROM region WHERE id = ? ")) {
            preparedStatement.setLong(1, regionId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLSurgelevelManager. Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public RegionEntity save(RegionEntity ticket) {
        return null;
    }

    @Override
    public void update(RegionEntity ticket) {

    }

    @Override
    public Optional<RegionEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<RegionEntity> findAll() {
        return null;
    }
}


