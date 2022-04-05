package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class StoresTenantsEntity {
    private Long id;
    private String tenantId;
    private Integer storeId;
}
