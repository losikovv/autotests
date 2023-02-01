package ru.instamart.jdbc.entity.customer_payments;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TransactionEntity {
    private Long id;
    private String uuid;
    private int invoiceId;
    private int paymentToolId;
    private int amount;
    private int holdAmount;
    private int refundAmount;
    private String state;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String merchantId;
    private String orderNumber;
}
