package ru.instamart.jdbc.entity.order_service;

import lombok.Data;

@Data
public class PlacesEntity {
    private String uuid;
    private Integer deliveryZoneId;
    private String placeLocation;
    private String assemblyTaskType;
    private String deliveryTaskType;
    private String placeAvailableTasksToBeAssigned;
    private int averagePerPositionAssemblyTime;
    private String createdAt;
    private String updatedAt;
    private int additionalSecondsForAssembly;
    private String retailerUuid;
    private int slaMin;
}
