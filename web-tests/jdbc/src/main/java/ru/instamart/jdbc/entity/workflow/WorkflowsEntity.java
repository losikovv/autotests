package ru.instamart.jdbc.entity.workflow;

import lombok.Data;

@Data
public class WorkflowsEntity {
    private Long id;
    private String performerUuid;
    private Integer status;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String planStartedAt;
    private String planEndedAt;
    private Long shiftId;
    private Integer cancellationReason;
    private String locationStart;
    private String locationEnd;
    private String shipments;
}
