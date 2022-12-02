package ru.instamart.jdbc.dao.orders_service;

import ru.instamart.jdbc.dao.Dao;
import ru.instamart.jdbc.entity.order_service.RetailersEntity;
import ru.instamart.jdbc.util.ConnectionManager;
import ru.instamart.jdbc.util.Db;
import ru.instamart.k8s.rails_response.model.Retailer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.fail;

public class RetailersDao implements Dao<String, RetailersEntity> {

    public static final RetailersDao INSTANCE = new RetailersDao();
    private final String SELECT_SQL = "SELECT %s FROM retailers";

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public RetailersEntity save(RetailersEntity ticket) {
        return null;
    }

    @Override
    public void update(RetailersEntity ticket) {

    }

    @Override
    public Optional<RetailersEntity> findById(String id) {
        return Optional.empty();
    }

    public RetailersEntity findByRetailerUuid(String retailerUuid) {
        RetailersEntity retailersEntity = null;
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ORDER).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE uuid = ?::uuid LIMIT 1")) {
            preparedStatement.setString(1, retailerUuid);
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    retailersEntity = new RetailersEntity();
                    retailersEntity.setUuid(resultSet.getString("uuid"));
                    retailersEntity.setVertical(resultSet.getString("vertical"));
                    retailersEntity.setCreatedAt(resultSet.getString("created_at"));
                    retailersEntity.setUpdatedAt(resultSet.getString("updated_at"));
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLOrderServiceManager. Error: " + e.getMessage());
        }
        return retailersEntity;
    }

    public RetailersEntity findByVertical() {
        try (final var connect = ConnectionManager.getDataSource(Db.PG_ORDER).getConnection();
             final var preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, "*") + " WHERE vertical = 'RESTAURANT' LIMIT 1")) {
            try (final var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    RetailersEntity retailersEntity;
                    retailersEntity = new RetailersEntity();
                    retailersEntity.setUuid(resultSet.getString("uuid"));
                    return retailersEntity;
                }
            }
        } catch (SQLException e) {
            fail("Error init ConnectionPgSQLOrderServiceManager. Error: " + e.getMessage());
        }
        return null;
    }


    @Override
    public List<RetailersEntity> findAll() {
        return null;
    }
}
