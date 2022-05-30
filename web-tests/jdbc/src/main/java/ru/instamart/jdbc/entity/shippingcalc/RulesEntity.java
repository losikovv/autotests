package ru.instamart.jdbc.entity.shippingcalc;

import lombok.Data;

@Data
public class RulesEntity {
    private Integer id;
    private Integer strategyId;
    private Integer scriptId;
    private String scriptParams;
    private Integer priority;
    private String createdAt;
    private String deletedAt;
    private String creatorId;
    private String ruleType;
}
