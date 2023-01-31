package ru.instamart.jdbc.entity.assembly;

import lombok.Data;

@Data
public class AssemblyEntity {

    private long prId;
    private String id;
    private long state;
    private java.sql.Timestamp createdAt;
    private java.sql.Timestamp updatedAt;
    private java.sql.Timestamp endedAt;
    private java.sql.Timestamp canceledAt;
    private long shipmentShpId;
    private String shipmentId;
    private String shipmentNumber;
    private String storeId;
    private String isFirstOrder;
    private String isHeavy;
    private String thermalBagNeeded;
    private double weight;
    private long itemsCount;
    private java.sql.Timestamp deadlineAt;
    private java.sql.Timestamp startedAt;
    private java.sql.Timestamp lockedAt;
    private java.sql.Timestamp dispatchAt;
    private java.sql.Timestamp startAfter;
    private long plannedCollectionDuration;
    private String userStoreBasket;
    private java.sql.Timestamp slotStart;
}
