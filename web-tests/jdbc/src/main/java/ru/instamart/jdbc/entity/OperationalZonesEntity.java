package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class OperationalZonesEntity {
    private Long id;
    private String name;
    private String createdAt;
    private String updatedAt;
}
