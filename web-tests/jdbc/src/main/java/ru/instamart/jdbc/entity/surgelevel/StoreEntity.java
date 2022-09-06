package ru.instamart.jdbc.entity.surgelevel;

import lombok.Data;

@Data
public class StoreEntity {
    private String id;
    private String configId;
    private Integer retailerId;
    private Integer regionId;
    private Boolean ondemand;
    private Float lat;
    private Float lon;
    private String actualResultId;
    private String predictResultId;
}