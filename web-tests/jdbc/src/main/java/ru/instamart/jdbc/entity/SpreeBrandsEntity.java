package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class SpreeBrandsEntity {
    private Long id;
    private String name;
    private String createdAt;
    private String updatedAt;
    private String permalink;
    private String keywords;
}
