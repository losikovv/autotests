package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class SpreeTaxonomiesEntity {
    private Long id;
    private String name;
    private String createdAt;
    private String updatedAt;
    private Integer position;
}
