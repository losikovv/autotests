package ru.instamart.jdbc.dao;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.jdbc.entity.OffersEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;
import ru.instamart.kraken.util.TimeUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testng.Assert.fail;

@Slf4j
public final class CompanyPaymentAccountsDao extends AbstractDao<Long, OffersEntity> {

    public static final CompanyPaymentAccountsDao INSTANCE = new CompanyPaymentAccountsDao();

    private static final String INSERT_SQL = "INSERT INTO " +
            "company_payment_accounts(company_id, balance, created_at, updated_at, refreshed_at) " +
            "VALUES( ?, ?, ?, ?, ?);";
    private static final String UPDATE_SQL = "UPDATE company_payment_accounts SET balance = ? WHERE company_id = ?";

    public void createCompanyAccount(Integer companyId, Integer balance) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(INSERT_SQL)) {
            preparedStatement.setInt(1, companyId);
            preparedStatement.setInt(2, balance);
            preparedStatement.setString(3, TimeUtil.getDateWithoutTime());
            preparedStatement.setString(4, TimeUtil.getDateWithoutTime());
            preparedStatement.setString(5, TimeUtil.getDateWithoutTime());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }

    public void updateBalance(Integer companyId, Integer balance) {
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setInt(1, balance);
            preparedStatement.setInt(2, companyId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
