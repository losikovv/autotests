package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class SpreeOptionTypesEntity {
    private Long id;
    private String name;
    private String presentation;
    private String position;
    private String createdAt;
    private String updatedAt;

    private String valueName;
    private String valuePresentation;
}
