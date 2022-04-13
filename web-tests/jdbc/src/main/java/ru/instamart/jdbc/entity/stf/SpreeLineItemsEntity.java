package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class SpreeLineItemsEntity {
    private Long id;
    private Long variantId;
    private Long orderId;
    private Integer quantity;
    private Double price;
    private String createdAt;
    private String updatedAt;
    private String currency;
    private Double costPrice;
    private Double retailerShelfPrice;
    private Integer itemsPerPack;
    private Long retailerId;
    private Long shipmentId;
    private Long offerId;
    private String uuid;
    private String  assemblyIssue;
    private String deletedAt;
    private Integer assembled;
    private Integer vatRate;
    private String  customerComment;
    private Double promoTotal;
    private String pricer;
    private Double unitPrice;
    private Double unitQuantity;
    private Double discount;
    private Double foundQuantity;
}
