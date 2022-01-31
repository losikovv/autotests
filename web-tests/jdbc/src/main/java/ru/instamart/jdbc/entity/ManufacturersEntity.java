package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class ManufacturersEntity {
    private Long id;
    private String name;
    private String created_at;
    private String updated_at;
}
