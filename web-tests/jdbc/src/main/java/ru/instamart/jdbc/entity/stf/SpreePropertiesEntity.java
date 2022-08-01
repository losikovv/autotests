package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class SpreePropertiesEntity {
    private Long id;
    private String name;
    private String presentation;
    private String createdAt;
    private String updatedAt;
}
