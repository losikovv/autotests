package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class SpreeProductsEntity {
    private Long id;
    private String name;
    private String description;
    private String deletedAt;
    private String permalink;
    private Integer shippingCategoryId;
    private String createdAt;
    private String updatedAt;
    private Integer popularity;
    private String lastImportedAt;
    private Long brandId;
    private Long manufacturerId;
    private Integer manufacturingCountryId;
    private String keywords;
    private Integer exportToYandexMarket;
    private String retailerDepartment;
    private String ean;
    private Integer exportToGoogleMerchant;
    private Integer exportToGoodsRu;
    private String sku;
    private String packType;
    private Integer masterCategoryId;
    private String promoKeywords;
    private String shortName;
}

