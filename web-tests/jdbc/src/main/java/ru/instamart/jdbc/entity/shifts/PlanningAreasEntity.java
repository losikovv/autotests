package ru.instamart.jdbc.entity.shifts;

import lombok.Data;

@Data
public class PlanningAreasEntity {

    private Long id;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String name;
    private Long regionId;
    private String poly;
    private String maxFixPayrollPerHour;
    private Boolean surged;
    private Long externalId;
    private Long storeId;
    private Long deliveryAreaId;
    private String maxFixPayrollPerHourByRole;
    private String surgedByRole;
    private Long maxShiftDuration;
}
