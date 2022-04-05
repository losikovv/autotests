package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class SpreeShipmentsEntity {

    private Long id;
    private String number;
    private Double cost;
    private String shippedAt;
    private Long orderId;
    private String state;
    private String createdAt;
    private String updatedAt;
    private Long stockLocationId;
    private Long deliveryWindowId;
    private Long retailerId;
    private String uuid;
    private String assemblyComment;
    private Integer storeId;
    private String shopperName;
    private String shopperLogin;
    private String driverName;
    private String driverLogin;
    private Double totalWeight;
    private String invoiceNumber;
    private Double invoiceTotal;
    private Double total;
    private Double promoTotal;
    private Double totalCost;
    private Double itemTotal;
    private Integer itemCount;
    private Integer totalQuantity;
    private Double  paymentTotal;
    private String driverPhone;
    private String vehicleNumber;
    private String shopperPhone;
    private Long  shippingCategoryId;
    private String prereplacementSelectingEndsAt;
    private Integer prereplacementSmsSent;
    private Integer expressDelivery;
    private Integer nextOrder;
    private Integer externalServiceState;
    private Integer externalId;
}
