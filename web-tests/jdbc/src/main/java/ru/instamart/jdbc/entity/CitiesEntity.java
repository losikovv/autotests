package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class CitiesEntity {
    private Long id;
    private String name;
    private String slug;
    private String createdAt;
    private String updatedAt;
    private Integer locked;
    private String nameIn;
    private String nameFrom;
    private String nameTo;
}

