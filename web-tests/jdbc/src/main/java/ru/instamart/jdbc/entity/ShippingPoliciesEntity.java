package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class ShippingPoliciesEntity {
    private Long id;
    private Long retailerId;
    private String createdAt;
    private String updatedAt;
    private String title;
}