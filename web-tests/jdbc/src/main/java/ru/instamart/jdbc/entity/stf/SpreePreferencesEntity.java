package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class SpreePreferencesEntity {
    private Long id;
    private String value;
    private String key;
    private String valueType;
    private String createdAt;
    private String updatedAt;
}
