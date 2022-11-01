package ru.instamart.jdbc.entity.norns;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class LocationsEntity {
    private String id;
    private long shopperId;
    private String location;
    private long time;
    private long speed;
    private Timestamp createdAt;
    private String userUuid;
    private Boolean isValid;
    private String invalidReason;
    private Boolean isSended;
    private Boolean isFakeGpsAppDetected;

}
