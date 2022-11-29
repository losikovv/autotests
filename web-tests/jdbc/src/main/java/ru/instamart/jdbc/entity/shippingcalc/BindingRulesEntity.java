package ru.instamart.jdbc.entity.shippingcalc;

import lombok.Data;

@Data
public class BindingRulesEntity {
    private Integer id;
    private Integer strategyId;
    private String shipping;
    private String tenantId;
    private Integer regionId;
    private Integer retailerId;
    private Boolean ondemand;
    private Integer labelId;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String description;
}
