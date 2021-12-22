package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class ShippingPolicyRulesEntity {
    private Long id;
    private String type;
    private Long shippingPolicyId;
    private String createdAt;
    private String updatedAt;
}