package ru.instamart.jdbc.dao.stf;


import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.dto.stf.PromotionCodesFilters;
import ru.instamart.jdbc.entity.stf.PromotionCodesEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static org.testng.Assert.fail;

public class PromotionCodesDao extends AbstractDao<Long, PromotionCodesEntity> {

    public static final PromotionCodesDao INSTANCE = new PromotionCodesDao();
    private static final String SQL_SELECT_PROMO_CODE = "SELECT " +
            "id, value, promotion_id, activated_at, created_at, updated_at, usage_limit " +
            "FROM promotion_codes";

    private static final String SQL_INSERT_PROMO_CODE = "INSERT INTO " +
            "promotion_codes(value, promotion_id, created_at, updated_at, usage_limit) " +
            "VALUES( ?, ?, ?, ?, ?);";

    public List<PromotionCodesEntity> findAll(PromotionCodesFilters filters) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();

        if (filters.getValue() != null) {
            whereSql.add("value LIKE ?");
            parameters.add(filters.getValue());
        }
        if (filters.getPromotionId() != null) {
            whereSql.add("promotion_id = ?");
            parameters.add(filters.getPromotionId());
        }
        if (filters.getId() != null) {
            whereSql.add("id = ?");
            parameters.add(filters.getId());
        }
        if (filters.getActivatedAt() != null) {
            whereSql.add("activated_at = ?");
            parameters.add(filters.getActivatedAt());
        }
        if (filters.getCreatedAt() != null) {
            whereSql.add("created_at = ?");
            parameters.add(filters.getCreatedAt());
        }
        if (filters.getUpdatedAt() != null) {
            whereSql.add("updated_at = ?");
            parameters.add(filters.getUpdatedAt());
        }
        if (filters.getUsageLimit() != null) {
            whereSql.add("usage_limit = ?");
            parameters.add(filters.getUsageLimit());
        }
        parameters.add(filters.getLimit());
        parameters.add(filters.getOffset());

        var where = whereSql.stream()
                .collect(joining(" AND ", " WHERE ", " LIMIT ? OFFSET ? "));
        var sql = SQL_SELECT_PROMO_CODE + where;

        try (var connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             var preparedStatement = connect.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            var resultQuery = preparedStatement.executeQuery();
            List<PromotionCodesEntity> result = new ArrayList<>();
            while (resultQuery.next()) {
                PromotionCodesEntity entity = new PromotionCodesEntity();
                entity.setValue(resultQuery.getString("value"));
                entity.setId(resultQuery.getLong("id"));
                result.add(entity);
            }
            return result;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return null;
    }

    public void updateUsageLimit(Integer usageLimit, String promoCode) {
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement("UPDATE promotion_codes SET usage_limit = ? WHERE value = ?")) {
            preparedStatement.setInt(1, usageLimit);
            preparedStatement.setString(2, promoCode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }

    public void createPromoCode(String value, Integer promotionId, String createdAt, String updatedAt, Integer usageLimit) {
        try (Connection connect = ConnectionManager.getConnection(Db.MYSQL_STF);
             PreparedStatement preparedStatement = connect.prepareStatement(SQL_INSERT_PROMO_CODE)) {
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, promotionId);
            preparedStatement.setString(3, createdAt);
            preparedStatement.setString(4, updatedAt);
            preparedStatement.setInt(5, usageLimit);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
    }
}
