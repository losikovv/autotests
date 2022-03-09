package ru.instamart.api.model.shopper.app;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblyItemSHP extends BaseObject {

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    public static class Data extends BaseObject {
        @JsonSchema(required = true)
        private String id;
        @JsonSchema(required = true)
        private String type;
        private Attributes attributes;
        @JsonSchema(ignore = true)
        private Relationships relationships;

        @lombok.Data
        @EqualsAndHashCode(callSuper=false)
        public static class Attributes extends BaseObject {
            @JsonSchema(required = true)
            private Integer assemblyId;
            @Null
            private Object issue;
            @Null
            private Object issueMessage;
            @JsonSchema(required = true)
            private Integer packs;
            @JsonSchema(required = true)
            private Integer itemsPerPack;
            @JsonSchema(required = true)
            private Integer quantity;
            @JsonSchema(required = true)
            private Integer foundQuantity;
            @JsonSchema(required = true)
            private Double size;
            @JsonSchema(required = true)
            private String humanSize;
            @JsonSchema(required = true)
            private Double weight;
            @Null
            private Object thumbnail;
            @JsonSchema(required = true)
            private String price;
            @JsonSchema(required = true)
            private String amount;
            private String retailerPrice;
            private String retailerAmount;
            private String humanVolume;
            @JsonSchema(required = true)
            private Integer pickupOrder;
            @JsonSchema(required = true)
            private String name;
            private String retailerName;
            private String retailerSku;
            @JsonSchema(required = true)
            private String productSku;
            @JsonSchema(required = true)
            private String state;
            @JsonSchema(required = true)
            private Double totalWeight;
            @JsonSchema(required = true)
            private String pricerType;
            @JsonSchema(required = true)
            private Integer qty;
            @Null
            private Object foundQty;
            private List<Object> foundQtys = null;
            private Integer originalQuantity;
            @JsonSchema(required = true)
            private Integer pcs;
            @Null
            private String productionDate;
            @Null
            private String bestBefore;
            private Integer vatRate;
            @JsonSchema(required = true)
            private String unit;
            @JsonSchema(required = true)
            private String priceWithDiscount;
            @Null
            private String updatedAt;
            private List<String> eans = null;
            private Boolean isTouched;
            private Boolean isMarked;
            private Object cancelReason;
            private Object replacement;
            private Object fromItemId;
            private Object additionalItemForReplacementId;
            private Object clarificationReason;
        }

        @lombok.Data
        @EqualsAndHashCode(callSuper=false)
        public static class Relationships extends BaseObject {
            private CancellationSHP cancellation;
            private ItemReturnSHP itemReturn;
        }
    }
}
