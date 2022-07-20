package ru.instamart.jdbc.entity.shifts;

import lombok.Data;

@Data
public class PlanningPeriodEntity {
    private Integer id;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private Integer planningAreaId;
    private String startedAt;
    private String endedAt;
    private Integer peoplesCount;
    private Boolean surged;
    private String mappedShops;
    private String importId;
    private String creatorId;
    private String role;
    private String guaranteedPayroll;
    private String predictedPayroll;
    private String publishedTimes;
    private String publishedTime;
    private Integer peoplesCountPredicted;
    private Boolean maxPeoplesCountEnabled;
}
