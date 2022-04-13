package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class MarketingSamplesEntity {
    private Long id;
    private String name;
    private String description;
    private String comment;
    private String startsAt;
    private String expiresAt;
    private String deletedAt;
    private String createdAt;
    private String updatedAt;
    private Long userId;
}
