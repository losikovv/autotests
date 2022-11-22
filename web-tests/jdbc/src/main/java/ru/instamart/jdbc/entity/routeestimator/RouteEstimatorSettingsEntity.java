package ru.instamart.jdbc.entity.routeestimator;

import lombok.Data;

@Data
public class RouteEstimatorSettingsEntity {
    private String place_uuid;
    private int avgParkingMinVehicle;
    private int averageSpeedForStraightDistanceToClientMin;
    private int additionalFactorForStraightDistanceToClientMin;
    private int orderTransferTimeFromAssemblyToDeliveryMin;
    private int avgToPlaceMinExternal;
    private int orderTransferTimeFromDeliveryToClientMin;
    private int orderReceiveTimeFromAssemblyToDeliveryMin;
    private int averagePerPositionAssemblyTime;
    private String placeLocation;
    private String createdAt;
    private String updatedAt;
    private int additionalAssemblyTime;
    private int operationalZoneId;
}
