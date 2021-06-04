package ru.instamart.api.model.shopper.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OfferSHP extends BaseObject {
    private Integer id;
    private String uuid;
    private Integer stock;
    @JsonProperty(value = "retailer_sku")
    private String retailerSku;

    @lombok.Data
    @EqualsAndHashCode(callSuper=false)
    public static class Data extends BaseObject {
            private String id;
            private String type;
            private Attributes attributes;

            @lombok.Data
            @EqualsAndHashCode(callSuper=false)
            public static class Attributes extends BaseObject {
                private String uuid;
                private String name;
                private String retailerSku;
                private String productSku;
                private String productName;
                private Double retailerPrice;
                private Integer retailerId;
                private String thumbnail;
                private Integer itemsPerPack;
                private Double size;
                private Double price;
                private Integer pickupOrder;
                private String humanVolume;
                private String pricerType;
                private Integer availableStock;
                private List<String> eans = null;
            }
    }
}
