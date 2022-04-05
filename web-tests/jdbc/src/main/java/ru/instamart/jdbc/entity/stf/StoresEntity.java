package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class StoresEntity {
    private Integer id;
    private String uuid;
    private Long locationId;
    private String createdAt;
    private String updatedAt;
    private Long retailerId;
    private Object zones;
    private String availableOn;
    private String timeZone;
    private Long operationalZoneId;
    private Integer hasConveyor;
    private Integer autoRouting;
    private Long cityId;
    private Integer expressDelivery;
    private Integer boxScanning;
    private Integer training;
    private Integer externalAssembly;
    private String healthcheckedAt;
    private Integer onDemand;
    private Integer deliveryTime;
    private String openingTime;
    private String closingTime;
    private Integer onDemandClosingDelta;
}
