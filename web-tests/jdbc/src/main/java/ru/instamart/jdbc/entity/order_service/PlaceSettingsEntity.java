package ru.instamart.jdbc.entity.order_service;

import lombok.Data;

@Data
public class PlaceSettingsEntity {
    private String placeUuid;
    private Integer maxOrderAssingRetryCount;
    private String createdAt;
    private String updatedAt;
    private String scheduleType;
    private Boolean autoRouting;
    private Integer periodForTimeToThrowMin;
    private Integer operationalZoneId;
}
