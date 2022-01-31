package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class ShipmentReviewWindowClosesEntity {
    private Long id;
    private Integer number;
    private Long shipmentId;
}
