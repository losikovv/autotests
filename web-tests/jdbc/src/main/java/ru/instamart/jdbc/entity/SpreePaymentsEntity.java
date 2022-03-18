package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class SpreePaymentsEntity {
    private Long id;
    private Double amount;
    private Long orderId;
    private Long sourceId;
    private String sourceType;
    private Integer paymentMethodId;
    private String state;
    private String responseCode;
    private String avsResponse;
    private String createdAt;
    private String updatedAt;
    private String identifier;
    private String cvvResponseCode;
    private String cvvResponseMessage;
    private Double holdAmount;
    private Double depositedAmount;
    private Integer holdAcquired;
    private Integer completionAttempt;
}
