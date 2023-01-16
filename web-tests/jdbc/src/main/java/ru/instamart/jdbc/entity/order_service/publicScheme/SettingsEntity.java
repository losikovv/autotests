package ru.instamart.jdbc.entity.order_service.publicScheme;

import lombok.Data;

@Data
public class SettingsEntity {
    private String placeUuid;
    private int maxOrderAssignRetryCount;
    private int avgParkingMinVehicle;
    private int maxCurrentOrderAssignQueue;
    private int orderWeightThresholdToAssignToVehicleGramms;
    private int averageSpeedForStraightDistanceToClientMin;
    private int additionalFactorForStraightDistanceToClientMin;
    private int orderTransferTimeFromAssemblyToDeliveryMin;
    private int avgToPlaceMinExternal;
    private int avgToPlaceMin;
    private int offerSeenTimeoutSec;
    private Boolean placeLocationCenter;
    private int lastPositionExpire;
    private Boolean taxiDeliveryOnly;
    private int orderTransferTimeFromDeliveryToClientMin;
    private int orderReceiveTimeFromAssemblyToDeliveryMin;
    private int offerServerTimeoutSec;
    private Boolean externalAssembliersPresented;
    private int gapTaxiPunishMin;
    private Boolean taxiAvailable;
    private int maxWaitingTimeForCourierMin;
    private String createdAt;
    private String updatedAt;
    private int rteFactorCityCongestionMinutes;
    private double rteDeliverySlotMultiplier;
}
