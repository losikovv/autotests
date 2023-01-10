package ru.instamart.jdbc.entity.shippingcalc;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RetailersEntity {
    private Integer id;
    private String uuid;
    private Integer vertical;
    private String createdAt;
    private String updatedAt;
}
