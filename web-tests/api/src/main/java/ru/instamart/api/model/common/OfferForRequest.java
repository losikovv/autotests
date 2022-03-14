package ru.instamart.api.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OfferForRequest {

    private OfferParams offer;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class OfferParams {
        private String name;
        @JsonProperty(value = "vat_rate")
        private Integer vatRate;
        @JsonProperty(value = "items_per_pack")
        private Integer itemsPerPack;
        private Integer stock;
        @JsonProperty(value = "max_stock")
        private Integer maxStock;
        @JsonProperty(value = "pickup_order")
        private Integer pickupOrder;
        @JsonProperty(value = "price_type")
        private String priceType;
        private Boolean published;
    }
}
