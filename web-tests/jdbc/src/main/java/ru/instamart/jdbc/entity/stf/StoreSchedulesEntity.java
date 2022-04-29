package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class StoreSchedulesEntity {

    private Long id;
    private Integer storeId;
    private String template;
    private String createdAt;
    private String updatedAt;
}
