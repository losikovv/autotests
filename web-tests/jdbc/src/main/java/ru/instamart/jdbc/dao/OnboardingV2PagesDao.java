package ru.instamart.jdbc.dao;

import ru.instamart.jdbc.entity.OnboardingV2PagesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class OnboardingV2PagesDao extends AbstractDao<Long, OnboardingV2PagesEntity> {

    public static final OnboardingV2PagesDao INSTANCE = new OnboardingV2PagesDao();
    private final String SELECT_SQL = "SELECT %s FROM onboarding_v2_pages";

    public int getCount() {
        int resultCount = 0;
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "COUNT(*) AS total") + " WHERE active = 1")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            resultCount = resultSet.getInt("total");
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return resultCount;
    }
}
