package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.InstacoinAccountsEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class InstacoinAccountsDao implements Dao<Long, InstacoinAccountsEntity>{

    public static final InstacoinAccountsDao INSTANCE = new InstacoinAccountsDao();

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public InstacoinAccountsEntity save(InstacoinAccountsEntity ticket) {
        return null;
    }

    @Override
    public void update(InstacoinAccountsEntity ticket) {

    }

    @Override
    public Optional<InstacoinAccountsEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<InstacoinAccountsEntity> findAll() {
        return null;
    }

    public void updatePromotionCode(Long promotionCodeId, Long userId) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format("UPDATE instacoin_accounts SET promotion_code_id = %s WHERE user_id = '%s'", promotionCodeId, userId))) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
