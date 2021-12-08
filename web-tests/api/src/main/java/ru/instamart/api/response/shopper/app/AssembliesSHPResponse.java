package ru.instamart.api.response.shopper.app;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.shopper.app.*;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssembliesSHPResponse extends BaseResponseObject {
    @JsonSchema(required = true)
    private List<AssemblySHP.Data> data = null;

    @JsonSchema(ignore = true)
    private List<Included> included = null;

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class Included extends BaseObject {
        private String id;
        private String type;
        @JsonSchema(ignore = true)
        private Attributes attributes;
        @JsonSchema(ignore = true)
        private ShipmentSHP.Data.Relationships relationships;

        @Data
        @EqualsAndHashCode(callSuper=false)
        public static class Attributes extends BaseObject {
            /**
             * Base attributes
             */
            private String number;
            /**
             * Shipment attributes
             */
            private Integer weight;
            private String logisticVolume;
            private String state;
            @Null
            private Object dispatcherComment;
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
            @Null
            private Object inspectionResult;
            private String total;
            private String cost;
            private String itemTotal;
            private String adjustmentTotal;
            @Null
            private String deliveryDate;
            @Null
            private String deliveryInterval;
            @Null
            private String customerComment;
            @Null
            private Object orderDispatcherComment;
            private AddressSHP address;
            private String retailerName;
            private String assemblyState;
            @Null
            private Object totalGreaterThanReceipt;
            private PaymentSHP payment;
            private ReplacementPolicySHP replacementPolicy;
            private Boolean isPaid;
            @Null
            private Object canBePrinted;
            private Boolean canChangePaymentTool;
            @Null
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
            /**
             * Package set attributes
             */
            private String location;
            private Object qty;
            private String boxNumber;
        }

    }
}
