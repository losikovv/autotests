package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class SpreeZonesEntity {

    private Long id;
    private String name;
    private String description;
    private Integer zoneMembersCount;
    private String createdAt;
    private String updatedAt;
}
