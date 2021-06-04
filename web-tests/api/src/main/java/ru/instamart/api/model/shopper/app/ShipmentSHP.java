package ru.instamart.api.model.shopper.app;

import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentSHP extends BaseObject {
    private Data data;

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    public static class Data extends BaseObject {
        private String id;
        private String type;
        private Attributes attributes;
        private Relationships relationships;

        @lombok.Data
        @EqualsAndHashCode(callSuper=false)
        public static class Relationships extends BaseObject {
            private AssemblySHP assembly;
            private AssemblyCheckListSHP assemblyCheckList;
            private TicketsSHP tickets;
            private LogisticAttributesSHP logisticAttributes;
        }

        @lombok.Data
        @EqualsAndHashCode(callSuper=false)
        public static class Attributes extends BaseObject {
            private String number;
            private Integer weight;
            private String logisticVolume;
            private String state;
            private String dispatcherComment;
            private Integer itemCount;
            private Boolean isOnlinePayment;
            private String tenantId;
            private String retailer;
            private Integer storeId;
            private String uuid;
            private String email;
            private String lifePayPaymentMethod;
            private Boolean isFirstOrder;
            private Boolean closingDocsRequired;
            private Boolean isOutdated;
            private List<Object> linkedShipments = null;
            private String deliveryBefore;
            private String inspectionResult;
            private String total;
            private String cost;
            private String itemTotal;
            private String adjustmentTotal;
            private String deliveryDate;
            private String deliveryInterval;
            private String customerComment;
            private String orderDispatcherComment;
            private AddressSHP address;
            private String retailerName;
            private String assemblyState;
            private Object totalGreaterThanReceipt;
            private PaymentSHP payment;
            private ReplacementPolicySHP replacementPolicy;
            private Boolean isPaid;
            private Object canBePrinted;
            private Boolean canChangePaymentTool;
            private String storeImportKey;
            private Object retailerCard;
            private String assemblyDeadlineStatus;
            private String assemblyDeadlineAt;
            private String deliveryDeadlineAt;
            private Boolean isPickup;
            private Boolean isAlcohol;
            private Boolean isTerminalState;
        }
    }
}
