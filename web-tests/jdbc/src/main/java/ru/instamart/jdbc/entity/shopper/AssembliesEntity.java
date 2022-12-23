package ru.instamart.jdbc.entity.shopper;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssembliesEntity {
        private int id;
        private int shipmentId;
        private String state;
        private String createdAt;
        private String updatedAt;
        private String invoiceNumber;
        private BigDecimal invoiceTotal;
        private BigDecimal receiptsTotal;
        private int activeItemsCount;
        private BigDecimal itemsTotal;
        private int driverId;
        private String shippedAt;
        private String originalShippingTotals;
        private BigDecimal logisticVolume;
        private String trolleyNumbers;
        private int packerId;
        private String marketingSampleItems;
        private String collectingStartedAt;
        private String purchasedAt;
        private int timeForCollecting;
        private String driverType;
}
