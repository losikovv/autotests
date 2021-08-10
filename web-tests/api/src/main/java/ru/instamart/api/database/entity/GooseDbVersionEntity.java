package ru.instamart.api.database.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GooseDbVersionEntity {
    private Integer id;
    private Long versionId;
    private Boolean isApplied;
    private LocalDateTime tstamp;
}
