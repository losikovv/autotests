package ru.instamart.jdbc.entity.customer_payments;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PaymentToolEntity {
    private int id;
    private String uuid;
    private int paymentMethodId;
    private String customerUuid;
    private String title;
    private String paymentToken;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String merchantId;
}
