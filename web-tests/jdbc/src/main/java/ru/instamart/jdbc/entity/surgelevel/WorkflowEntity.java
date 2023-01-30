package ru.instamart.jdbc.entity.surgelevel;

import lombok.Data;

@Data
public class WorkflowEntity {
    private Integer id;
    private Integer status;
    private String candidateId;
    private Integer shiftId;
    private Float coordinatesEndLat;
    private Float coordinatesEndLon;
    private String planEndedAt;
    private String createdAt;
    private String updatedAt;
}
