package ru.instamart.jdbc.entity.shippingcalc;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SurgeParametersEntity {
    private Integer id;
    private Integer regionId;
    private Integer vertical;
    private String parameters;
}
