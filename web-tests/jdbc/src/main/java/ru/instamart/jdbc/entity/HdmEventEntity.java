package ru.instamart.jdbc.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class HdmEventEntity {
    private Integer id;
    private UUID deliveryAreaBaseStoreUuid;
    private Boolean highDemand;
    private LocalDateTime createdAt;
}
