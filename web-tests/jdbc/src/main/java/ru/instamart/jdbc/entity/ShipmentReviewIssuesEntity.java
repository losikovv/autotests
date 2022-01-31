package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class ShipmentReviewIssuesEntity {
    private Long id;
    private Integer position;
    private String description;
    private String kind;
    private String createdAt;
    private String updatedAt;
    private Integer active;
    private Integer commentNeeded;
    private Integer minRate;
    private Integer maxRate;
}
