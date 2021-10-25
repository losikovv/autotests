package ru.instamart.jdbc.dao;


import ru.instamart.jdbc.dto.PromotionCodesFilters;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class PromotionCodesDao implements Dao {

    public static final PromotionCodesDao INSTANCE = new PromotionCodesDao();
    private static final String SQL_SELECT_PROMO_CODE = "SELECT " +
            "id, value, promotion_id, activated_at, created_at, updated_at, usage_limit " +
            "FROM promotion_codes";
    private List<String> result = new ArrayList<>();

    private PromotionCodesDao() {
    }

    @Override
    public boolean delete(Object id) {
        return false;
    }

    @Override
    public Object save(Object ticket) {
        return null;
    }

    @Override
    public void update(Object ticket) {

    }

    @Override
    public Optional findById(Object id) {
        return Optional.empty();
    }

    @Override
    public List<String> findAll() {
        return null;
    }

    public List<String> findAll(PromotionCodesFilters filters) {
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

        try (var connect = ConnectionMySQLManager.get();
             var preparedStatement = connect.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            var resultQuery = preparedStatement.executeQuery();
            while (resultQuery.next()) {
                result.add(resultQuery.getString("value"));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
