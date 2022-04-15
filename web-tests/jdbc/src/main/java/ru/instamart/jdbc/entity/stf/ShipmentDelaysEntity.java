package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class ShipmentDelaysEntity {
    private Long id;
    private Long shipmentId;
    private Long deliveryWindowId;
    private Integer state;
    private String data;
    private String notificationSentAt;
    private String deadlineAt;
    private String collectingAt;
    private String shippingAt;
    private String shippedAt;
    private String canceledAt;
    private String collectingExpectedAt;
    private String shippingExpectedAt;
    private String shippedExpectedAt;
    private String createdAt;
    private String updatedAt;
    private String anonymousId;
    private String abSendNotification;
}
