package ru.instamart.jdbc.entity.eta;

import lombok.Data;

@Data
public class RetailerParametersEntity {
    private Long id;
    private Integer courierSpeed;
    private String deliveryTimeSigma;
    private String window;
}
