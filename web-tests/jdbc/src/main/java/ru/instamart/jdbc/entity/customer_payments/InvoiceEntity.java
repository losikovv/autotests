package ru.instamart.jdbc.entity.customer_payments;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class InvoiceEntity {
    private int id;
    private String shipmentUuid;
    private String customerUuid;
    private int totalAmount;
    private int totalHoldAmount;
    private int totalRefundAmount;
    private String state;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
