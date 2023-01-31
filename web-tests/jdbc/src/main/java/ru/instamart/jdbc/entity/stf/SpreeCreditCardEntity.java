package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class SpreeCreditCardEntity {
    private long id;
    private String month;
    private String year;
    private String ccType;
    private String lastDigits;
    private String firstName;
    private String lastName;
    private long addressId;
    private String gatewayCustomerProfileId;
    private String gatewayPaymentProfileId;
    private java.sql.Timestamp createdAt;
    private java.sql.Timestamp updatedAt;
    private long userId;
    private long active;
    private String name;
    private String md;
    private String acsUrl;
    private String paReq;
    private String state;
    private java.sql.Timestamp deletedAt;
    private String title;
    private String termUrl;
    private long ownerId;
    private String ownerType;
    private java.sql.Timestamp authorizedAt;
    private String firstDigits;
    private long corporate;
}
