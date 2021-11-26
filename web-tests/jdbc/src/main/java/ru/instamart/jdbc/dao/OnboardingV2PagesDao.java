package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.OnboardingV2PagesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OnboardingV2PagesDao implements Dao<Long, OnboardingV2PagesEntity>{

    public static final OnboardingV2PagesDao INSTANCE = new OnboardingV2PagesDao();
    private final String SELECT_SQL = "SELECT %s FROM onboarding_v2_pages";

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public OnboardingV2PagesEntity save(OnboardingV2PagesEntity ticket) {
        return null;
    }

    @Override
    public void update(OnboardingV2PagesEntity ticket) {

    }

    @Override
    public Optional<OnboardingV2PagesEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<OnboardingV2PagesEntity> findAll() {
        return null;
    }

    public int getCount() {
        int resultCount = 0;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total") + " WHERE active = 1")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            resultCount = resultSet.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultCount;
    }
}
