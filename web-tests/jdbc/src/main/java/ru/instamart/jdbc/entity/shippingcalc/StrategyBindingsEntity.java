package ru.instamart.jdbc.entity.shippingcalc;

import lombok.Data;

@Data
public class StrategyBindingsEntity {
    private Integer strategyId;
    private String storeId;
    private String tenantId;
    private String shipping;
}
