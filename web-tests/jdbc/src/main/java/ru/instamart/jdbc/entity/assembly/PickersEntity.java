package ru.instamart.jdbc.entity.assembly;

import lombok.Data;

@Data
public class PickersEntity {
    private long id;
    private String uuidId;
    private java.sql.Timestamp createdAt;
    private java.sql.Timestamp updatedAt;
    private Boolean active;
    private long shpId;
    private String storeId;
    private long storeShpId;
    private java.sql.Timestamp lastShpEventDate;
    private java.sql.Timestamp lockedAt;
    private Boolean dispatchOn;
    private java.sql.Timestamp shiftsEventCreatedAt;
    private java.sql.Timestamp readyAt;
    private String shipmentIds;
    private String role;
    private long employmentType;
    private Integer shiftId;
    private String fullName;
    private String phoneNumber;
    private Boolean hasPausedShipments;
    private long version;
    private long freeFor;
    private String hasOffer;
    private String offerId;
    private java.sql.Timestamp readyToDispatchAt;
    private java.sql.Timestamp syncedWithShpAt;
}
