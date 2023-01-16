package ru.instamart.jdbc.entity.order_service.publicScheme;

import lombok.Data;

@Data
public class OrdersEntity {
    private String shipmentUuid;
    private String placeUuid;
    private Double weight;
    private String clientLocation;
    private String status;
    private String type;
    private Integer itemCount;
    private String deliveryPromiseUpperDttm;
    private String createdAt;
    private String timeToThrowDttm;
    private String stfOrderUuid;
    private String updatedAt;
    private Integer assemblyTimeMin;
    private Integer straightDistanceToClientMin;
    private String assembly;
    private String delivery;
    private String settings;
    private String placeLocation;
    private Boolean sent;
    private String deletedAt;
    private String deliveryPromiseLowerDttm;
    private String orderNumber;
    private Double itemsTotalAmount;
    private Boolean isNew;
    private String orderStatus;
    private String lastRedispatchTime;
    private String orderUuid;
    private String shippingMethod;
    private Integer sendToDispatchCount;
}
