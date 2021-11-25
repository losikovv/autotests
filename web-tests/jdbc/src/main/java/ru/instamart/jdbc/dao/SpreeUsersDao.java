package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.SpreeUsersEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SpreeUsersDao implements Dao<Long, SpreeUsersEntity> {

    public static final SpreeUsersDao INSTANCE = new SpreeUsersDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_users";

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public SpreeUsersEntity save(SpreeUsersEntity ticket) {
        return null;
    }

    @Override
    public void update(SpreeUsersEntity ticket) {

    }

    @Override
    public Optional<SpreeUsersEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<SpreeUsersEntity> findAll() {
        return null;
    }

    public Long getIdByEmail(String email) {
        Long id = null;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "id") + " WHERE email = ?")) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getLong("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
