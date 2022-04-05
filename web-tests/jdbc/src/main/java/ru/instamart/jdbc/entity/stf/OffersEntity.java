package ru.instamart.jdbc.entity.stf;

import lombok.Data;

import java.sql.Timestamp;

@Data
public final class OffersEntity {

    private Long id;
    private Integer retailerId;
    private Long productId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String productSku;
    private Integer pickupOrder;
    private String name;
    private String retailerSku;
    private String uuid;
    private String rsku;
    private Integer vatRate;
    private Timestamp deletedAt;
    private Integer itemsPerPack;
    private Integer storeId;
    private String displayPriceAffix;
    private String pricer;
    private Integer gramsPerUnit;
    private Float quantityIncrement;
    private Float quantityInitial;
    private String quantityAffix;
    private String quantityType;
    private Integer stock;
    private Integer maxStock;
    private Integer published;
}
