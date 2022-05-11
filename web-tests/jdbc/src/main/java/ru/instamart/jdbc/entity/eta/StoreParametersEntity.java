package ru.instamart.jdbc.entity.eta;

import lombok.Data;

@Data
public class StoreParametersEntity {
    private String id;
    private Long retailerId;
    private Float lat;
    private Float lon;
    private Boolean isMlEnabled;
    private Integer avgPositionsPerPlace;
    private String toPlaceSec;
    private String collectionSpeedSecPerPos;
    private String storeOpeningTime;
    private String storeClosingTime;
    private String onDemandClosingDelta;
    private String timezone;
    private Boolean isSigmaEnabled;
}
