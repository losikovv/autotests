package ru.instamart.jdbc.entity.candidates;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class LocationsEntity {
    private long id;
    private Timestamp createdAt;
    private String candidateUuid;
    private String point;
    private String updatedAt;
    private String notifiedAt;
    private String coordinates;
    private boolean isFakeGpsAppDetected;
}
