package ru.instamart.jdbc.dao.stf;

import ru.instamart.jdbc.dao.AbstractDao;
import ru.instamart.jdbc.entity.stf.SpreeAddressesEntity;
import ru.instamart.jdbc.util.ConnectionMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.fail;

public class SpreeAddressesDao extends AbstractDao<Long, SpreeAddressesEntity> {

    public static final SpreeAddressesDao INSTANCE = new SpreeAddressesDao();
    private final String SELECT_SQL = "SELECT %s FROM spree_addresses";

    public SpreeAddressesEntity getAddressByUserPhone(String phone) {
        SpreeAddressesEntity address = new SpreeAddressesEntity();
        try (Connection connect = ConnectionMySQLManager.get();
             PreparedStatement preparedStatement = connect.prepareStatement(String.format(SELECT_SQL, '*') +
                     " WHERE phone LIKE ?")) {
            preparedStatement.setString(1, '%' + phone);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                address.setId(resultSet.getLong("id"));
                address.setStreet(resultSet.getString("street"));
                address.setCity(resultSet.getString("city"));
                address.setBuilding(resultSet.getString("building"));
                address.setBuilding(resultSet.getString("building"));
                address.setApartment(resultSet.getString("apartment"));
                address.setFullAddress(resultSet.getString("full_address"));
                address.setLat(resultSet.getDouble("lat"));
                address.setLon(resultSet.getDouble("lon"));
            } else return null;
        } catch (SQLException e) {
            fail("Error init ConnectionMySQLManager. Error: " + e.getMessage());
        }
        return address;
    }
}
