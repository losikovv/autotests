package ru.instamart.jdbc.entity.surgelevel;

import lombok.Data;

@Data
public class SupplyEntity {
    private String storeId;
    private String candidateId;
    private String id;
    private Double distance;
}
