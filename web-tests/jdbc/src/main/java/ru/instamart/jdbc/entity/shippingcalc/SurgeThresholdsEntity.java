package ru.instamart.jdbc.entity.shippingcalc;

import lombok.Data;

@Data
public class SurgeThresholdsEntity {
    private Integer regionId;
    private String parameters;
}
