package ru.instamart.jdbc.entity.surgelevel;

import lombok.Data;

@Data
public class CandidateEntity {
    private String id;
    private Integer role;
    private Float lat;
    private Float lon;
    private Integer deliveryArea;
    private Boolean busy;
    private Boolean ondemand;
    private Boolean onshift;
    private Boolean fakegps;
    private String createdAt;
    private String updatedAt;
    private String expiredAt;
}
