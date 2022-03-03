package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class ShippingMethodKindsEntity {

    private Long id;
    private String name;
    private String slug;
}
