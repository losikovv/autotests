package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class StoreZonesEntity {
    private Long id;
    private String name;
    private Long storeId;
    private Object area;
    private String createdAt;
    private String updatedAt;
    private String uuid;
}
