package ru.instamart.jdbc.entity.eta;

import lombok.Data;

@Data
public class ServiceParametersEntity {
    private String waitMlTimeout;
    private Boolean isMlEnabled;
    private Integer avgPositionsPerPlace;
    private String toPlaceSec;
    private String collectionSpeedSecPerPos;
    private String storeOpeningTime;
    private String storeClosingTime;
    private String onDemandClosingDelta;
    private Integer courierSpeed;
    private String deliveryTimeSigma;
    private String window;
    private Boolean isSigmaEnabled;
    private Integer courierSpeedDelivery;
    private Float curveFactorDelivery;
    private String routeEstimatorTimeout;
}
