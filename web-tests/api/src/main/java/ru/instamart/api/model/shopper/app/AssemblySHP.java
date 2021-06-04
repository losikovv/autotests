package ru.instamart.api.model.shopper.app;

import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class AssemblySHP extends BaseObject {
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
        public static class Attributes extends BaseObject {
            private Integer shipmentId;
            private String state;
            private Double weight;
            private String logisticVolume;
            private String total;
            private String retailerTotal;
            private OriginalShippingTotalsSHP originalShippingTotals;
            private Object shippedAt;
            private List<Object> trolleyNumbers = null;
            private Object packerId;
            private String collectingStartedAt;
            private Object purchasedAt;
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
