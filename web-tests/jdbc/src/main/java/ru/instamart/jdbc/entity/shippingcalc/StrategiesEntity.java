package ru.instamart.jdbc.entity.shippingcalc;

import lombok.Data;

@Data
public class StrategiesEntity {
    private Integer id;
    private String name;
    private String shipping;
    private Boolean global;
    private Integer priority;
    private String description;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String creatorId;
}
