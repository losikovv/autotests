package ru.instamart.jdbc.entity.shifts;

import lombok.Data;

@Data
public class ShopsEntity {
    private Integer id;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String name;
    private Integer regionId;
    private Integer deliveryAreaId;
    private String point;
    private String uuid;
    private Integer originalId;
    private Integer baseStoreId;
    private String availableOn;
    private Boolean expressDelivery;
    private String openingTime;
    private String closingTime;
    private String scheduleType;
}
