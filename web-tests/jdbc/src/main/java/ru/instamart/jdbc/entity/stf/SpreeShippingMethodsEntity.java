package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class SpreeShippingMethodsEntity {
    private Long id;
    private String name;
    private String displayOn;
    private String deletedAt;
    private String createdAt;
    private String updatedAt;
    private String trackingUrl;
    private String adminName;
    private String kind;
    private Integer shippingMethodKindId;
}
