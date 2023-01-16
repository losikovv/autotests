package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.CompanyEmployeesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.fail;

public class CompanyEmployeesDao extends AbstractDao<Integer, CompanyEmployeesEntity> {

    public static final CompanyEmployeesDao INSTANCE = new CompanyEmployeesDao();
    private final String SELECT_SQL = "SELECT %s FROM company_employees";

    public Optional<CompanyEmployeesEntity> findById(Integer id) {
        try (final var connect = ConnectionManager.getDataSource(Db.MYSQL_STF).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE id = ?")) {
            preparedStatement.setObject(1, id);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    final var companyEmployeesEntity = new CompanyEmployeesEntity();
                    companyEmployeesEntity.setId(resultSet.getInt("id"));
                    companyEmployeesEntity.setUserId(resultSet.getInt("user_id"));
                    companyEmployeesEntity.setCompanyId(resultSet.getInt("company_id"));
                    companyEmployeesEntity.setApproved(resultSet.getInt("approved"));
                    return Optional.ofNullable(companyEmployeesEntity);
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
