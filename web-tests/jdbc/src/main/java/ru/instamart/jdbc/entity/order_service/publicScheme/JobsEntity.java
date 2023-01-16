package ru.instamart.jdbc.entity.order_service.publicScheme;

import lombok.Data;

@Data
public class JobsEntity {
    public String jobId;
    public String jobType;
    public String status;
    public String meta;
    public java.sql.Timestamp createdAt;
    public java.sql.Timestamp updatedAt;
    public java.sql.Timestamp deletedAt;
    public String shipmentUUID;
}
