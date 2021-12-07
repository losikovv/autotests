package ru.instamart.api.model.shopper.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class OfferSHP extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;
    @JsonSchema(required = true)
    private String uuid;
    @JsonSchema(required = true)
    private Integer stock;
    @JsonSchema(required = true)
    @JsonProperty(value = "retailer_sku")
    private String retailerSku;

    @lombok.Data
    @EqualsAndHashCode(callSuper = false)
    public static class Data extends BaseObject {
        @JsonSchema(required = true)
        private String id;
        @JsonSchema(required = true)
        private String type;
        @JsonSchema(required = true)
        private Attributes attributes;

        @lombok.Data
        @EqualsAndHashCode(callSuper = false)
        public static class Attributes extends BaseObject {
            @JsonSchema(required = true)
            private String uuid;
            @JsonSchema(required = true)
            private String name;
            private String retailerSku;
            private String productSku;
            private String productName;
            private Double retailerPrice;
            private Integer retailerId;
            @Null
            private String thumbnail;
            private Integer itemsPerPack;
            private Double size;
            @JsonSchema(required = true)
            private Double price;
            private Integer pickupOrder;
            private String humanVolume;
            private String pricerType;
            private Integer availableStock;
            private List<String> eans = null;
        }
    }
}
