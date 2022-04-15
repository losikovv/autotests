package ru.instamart.jdbc.entity.workflow;

import lombok.Data;

@Data
public class AssignmentsEntity {
    private Long id;
    private Long workflowId;
    private Long postponedParentId;
    private String performerUuid;
    private Integer status;
    private String shipments;
    private String shipmentsIds;
    private Integer planPayroll;
    private String meta;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private Integer deliveryType;
    private String uuid;
    private String postponedParentUuid;
    private Integer performerVehicle;
    private Integer planPayrollBase;
    private Integer planPayrollBonus;
    private Integer sourceType;
    private String shift;
    private Integer timeout;
    private Boolean isAssembly;
    private Boolean isDelivery;
    private Integer duration;
    private Integer distance;
    private String expiredAt;
    private String performerName;
    private String performerLogin;
    private String performerPhone;
}
