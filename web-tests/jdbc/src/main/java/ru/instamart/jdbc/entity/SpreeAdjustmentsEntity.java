package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class SpreeAdjustmentsEntity {
    private Long id;
    private Integer sourceId;
    private String sourceType;
    private Integer adjustableId;
    private String adjustableType;
    private Integer originatorId;
    private String originatorType;
    private Double amount;
    private String label;
    private Integer mandatory;
    private Integer eligible;
    private String createdAt;
    private String updatedAt;
    private String state;
}
