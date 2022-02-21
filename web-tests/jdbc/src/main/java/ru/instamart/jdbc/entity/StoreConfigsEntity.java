package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class StoreConfigsEntity {
    private Long id;
    private Integer storeId;
    private String lifepayIdentifier;
    private String importKey;
    private Double shipmentBaseWeight;
    private Integer minOrderAmount;
    private Integer minFirstOrderAmount;
    private String createdAt;
    private String updatedAt;
    private Integer importVersion;
    private Integer shipmentBaseItemsCount;
    private Integer minOrderAmountPickup;
    private Integer minFirstOrderAmountPickup;
    private Integer secondsForAssemblyItems;
    private Integer additionalSecondsForAssembly;
    private Integer disallowOrderEditingHours;
    private Integer hoursOrderEditLocked;
    private String inn;
    private String legalName;
    private Integer externalAssemblyEnabled;
    private Integer externalAssemblyKind;
    private String ogrn;
    private String email;
    private String phone;
    private Integer useWorkSchedule;
}
