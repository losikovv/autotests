package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class FiscalReceiptsEntity {
    private Long id;
    private Long shipmentId;
    private Double total;
    private String transactionDetails;
    private String fiscalSecret;
    private String fiscalDocumentNumber;
    private String fiscalChecksum;
    private String paidAt;
    private String createdAt;
    private String updatedAt;
    private String syncState;
    private String nextSyncAfter;
    private String userInn;
    private Integer operationType;
    private String operator;
}
