package ru.instamart.api.database.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class HdmStatusEntity {
    private UUID deliveryAreaBaseStoreUuid;
    private Boolean active;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
