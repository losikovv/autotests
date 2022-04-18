package ru.instamart.jdbc.entity.workflow;

import lombok.Data;

@Data
public class SegmentsEntity {
    private Long id;
    private Long workflowId;
    private Integer type;
    private Integer position;
    private String shipments;
    private String locationStart;
    private String planStartedAt;
    private String locationEnd;
    private String planEndedAt;
    private String factStartedAt;
    private String factEndedAt;
    private Integer timeLag;
    private String meta;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private Long duration;
    private Integer distance;
    private String factLocationStart;
    private String factLocationEnd;
    private String storeName;
    private String storeAddress;
    private String status;
}
