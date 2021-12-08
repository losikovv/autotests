package ru.instamart.api.model.shopper.app;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class AssemblySHP extends BaseObject {
    @Null
    @JsonSchema(required = true)
    private Data data;

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    public static class Data extends BaseObject {
        private String id;
        private String type;
        private Attributes attributes;
        @JsonSchema(ignore = true)
        private Relationships relationships;

        @lombok.Data
        @EqualsAndHashCode(callSuper=false)
        public static class Attributes extends BaseObject {
            @JsonSchema(required = true)
            private Integer shipmentId;
            @JsonSchema(required = true)
            private String state;
            private Double weight;
            private String logisticVolume;
            @JsonSchema(required = true)
            private String total;
            private String retailerTotal;
            @Null
            private OriginalShippingTotalsSHP originalShippingTotals;
            @Null
            private Object shippedAt;
            private List<Object> trolleyNumbers = null;
            @Null
            private Object packerId;
            @Null
            private String collectingStartedAt;
            @Null
            private Object purchasedAt;
            @Null
            private Integer timeForCollecting;
            private String shipmentNumber;
        }

        @lombok.Data
        @EqualsAndHashCode(callSuper=false)
        public static class Relationships extends BaseObject {
            private ShipmentSHP shipment;
            private ItemsSHP items;
            private PackageSetsSHP packageSets;
        }
    }
}
