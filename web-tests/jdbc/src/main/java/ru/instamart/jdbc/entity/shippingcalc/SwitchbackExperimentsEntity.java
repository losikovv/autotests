package ru.instamart.jdbc.entity.shippingcalc;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SwitchbackExperimentsEntity {
    private String startDateTime;
    private String endDateTime;
    private Integer regionId;
    private String group;
    private String parameters;
}
