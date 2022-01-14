package ru.instamart.api.model.shopper.app;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentSHP extends BaseObject {
    @JsonSchema(required = true)
    private Data data;

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    public static class Data extends BaseObject {
        @JsonSchema(required = true)
        private String id;
        @JsonSchema(required = true)
        private String type;
        @JsonSchema(required = true)
        private Attributes attributes;
        @JsonSchema(ignore = true)
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
            @JsonSchema(required = true)
            private String number;
            @JsonSchema(required = true)
            private Integer weight;
            @Null
            private String logisticVolume;
            @JsonSchema(required = true)
            private String state;
            @Null
            private String dispatcherComment;
            @JsonSchema(required = true)
            private Integer itemCount;
            private Boolean isOnlinePayment;
            @Null
            private String tenantId;
            @Null
            private String retailer;
            @JsonSchema(required = true)
            private Integer storeId;
            private String uuid;
            @Null
            private String email;
            @Null
            private String lifePayPaymentMethod;
            private Boolean isFirstOrder;
            private Boolean closingDocsRequired;
            private Boolean isOutdated;
            private List<Object> linkedShipments = null;
            @Null
            private String deliveryBefore;
            @Null
            private String inspectionResult;
            @JsonSchema(required = true)
            private String total;
            private String cost;
            private String itemTotal;
            @Null
            private String adjustmentTotal;
            @Null
            private String deliveryDate;
            @Null
            private String deliveryInterval;
            @Null
            private String customerComment;
            @Null
            private String orderDispatcherComment;
            @JsonSchema(required = true)
            private AddressSHP address;
            private String retailerName;
            @Null
            private String assemblyState;
            @Null
            private Object totalGreaterThanReceipt;
            private PaymentSHP payment;
            @Null
            private ReplacementPolicySHP replacementPolicy;
            private Boolean isPaid;
            @Null
            private Object canBePrinted;
            private Boolean canChangePaymentTool;
            private String storeImportKey;
            @Null
            private Object retailerCard;
            @Null
            private String assemblyDeadlineStatus;
            @Null
            private String assemblyDeadlineAt;
            @Null
            private String deliveryDeadlineAt;
            private Boolean isPickup;
            private Boolean isAlcohol;
            private Boolean isTerminalState;
        }
    }
}
