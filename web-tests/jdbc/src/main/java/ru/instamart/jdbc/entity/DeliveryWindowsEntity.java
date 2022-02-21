package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class DeliveryWindowsEntity {
    private Long id;
    private Integer serial;
    private String startsAt;
    private String endsAt;
    private Integer shipmentsLimit;
    private Integer shipmentsCount;
    private Integer active;
    private String createdAt;
    private String updatedAt;
    private Integer storeId;
    private String timeZone;
    private Integer surgeAmount;
    private Integer shipmentBaseWeight;
    private Integer shipmentTotalWeight;
    private Integer shipmentExcessWeight;
    private Integer shipmentMinWeight;
    private String state;
    private Integer shipmentBaseItemsCount;
    private Integer shipmentTotalItemsCount;
    private Integer shipmentExcessItemsCount;
    private Integer shipmentMaxWeight;
    private String kind;
    private String uuid;
    private Integer closingTimeGap;
}
