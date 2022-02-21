package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class PricesEntity {
    private Long id;
    private Double price;
    private Double discount;
    private String productSku;
    private String tenantId;
    private Long offerId;
    private Integer storeId;
    private Integer published;
    private String createdAt;
    private String updatedAt;
    private Double retailerPrice;
    private Double offerPrice;
    private Double offerRetailerPrice;
    private Double costPrice;
    private String discountStartsAt;
    private String discountEndsAt;
    private Integer minImportVersion;
    private Integer maxImportVersion;
}
