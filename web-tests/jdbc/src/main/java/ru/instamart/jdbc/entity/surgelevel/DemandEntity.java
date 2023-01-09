package ru.instamart.jdbc.entity.surgelevel;

import lombok.Data;

@Data
public class DemandEntity {
    private String storeId;
    private String shipmentId;
    private String id;
    private Double distance;
}
