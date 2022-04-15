package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class SpreeRetailersEntity {
    private Long id;
    private String name;
    private String color;
    private String environment;
    private String description;
    private String key;
    private Integer homePageDepartmentsDepth;
    private String createdAt;
    private String updatedAt;
    private String slug;
    private String shortName;
    private String logoBackgroundColor;
    private String secondaryColor;
    private Integer priceAcceptableDiscrepancy;
    private Integer checkPriceDiscrepancy;
    private Integer position;
    private Long retailerAgreementId;
    private String inn;
    private String legalAddress;
    private String phone;
    private String legalName;
    private String contractType;
    private String uuid;
}
