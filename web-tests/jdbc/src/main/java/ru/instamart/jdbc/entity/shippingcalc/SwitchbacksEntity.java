package ru.instamart.jdbc.entity.shippingcalc;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SwitchbacksEntity {
    private Integer id;
    private String startDateTime;
    private String endDateTime;
    private Integer regionId;
    private Integer vertical;
    private String testGroup;
    private String parameters;
}
