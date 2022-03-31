package ru.instamart.jdbc.entity;

import lombok.Data;

@Data
public class TenantsEntity {
    private String id;
    private String name;
    private String hostname;
}
