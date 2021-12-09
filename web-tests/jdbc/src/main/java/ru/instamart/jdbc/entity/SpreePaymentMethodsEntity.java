package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class SpreePaymentMethodsEntity {
    private Long id;
    private String type;
    private String name;
    private String description;
    private Integer active;
    private String environment;
    private String deletedAt;
    private String createdAt;
    private String updatedAt;
    private String displayOn;
    private Integer position;
}
