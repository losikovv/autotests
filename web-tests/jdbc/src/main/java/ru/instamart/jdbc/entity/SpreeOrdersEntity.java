package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class SpreeOrdersEntity {
    private Long id;
    private String number;
    private Integer itemTota;
    private Integer total;
    private String state;
    private Integer adjustmentTotal;
    private Long userId;
    private String completedAt;
    private Long shipAddressId;
    private Integer paymentTotal;
    private String shipmentState;
    private String paymentState;
    private String email;
    private String specialInstructions;
    private String createdAt;
    private String updatedAt;
    private String currency;
    private String lastIpAddress;
    private Integer sendEmails;
    private String deliveryDate;
    private String deliveryTime;
    private String assemblyComment;
    private String deliveredAt;
    private Integer createdById;
    private Integer companyDocumentId;
    private String uuid;
    private Integer shipmentTotal;
    private Integer replacementPolicyId;
    private Integer closingDocsRequired;
    private String tenantIdBackup;
    private String tenantId;
    private String shippingMethodKind;
    private Integer expressDelivery;
    private Integer ownerId;
    private String ownerType;
    private Long paymentToolId;
}
