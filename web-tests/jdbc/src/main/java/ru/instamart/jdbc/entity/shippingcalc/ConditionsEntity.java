package ru.instamart.jdbc.entity.shippingcalc;

import lombok.Data;

@Data
public class ConditionsEntity {
    private Integer ruleId;
    private String params;
    private String conditionType;
}
