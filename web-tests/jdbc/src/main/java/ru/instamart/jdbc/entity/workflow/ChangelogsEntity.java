package ru.instamart.jdbc.entity.workflow;

import lombok.Data;

@Data
public class ChangelogsEntity {
    private Long id;
    private Long workflowId;
    private Long segmentId;
    private String factLocationStart;
    private String factStartedAt;
    private String timingsBefore;
    private String timingsAfter;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
}
