package ru.instamart.api.model.shadowcat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ShipmentSC extends BaseObject {

        private String id;

        @JsonProperty("region_id")
        private String regionId;

        @JsonProperty("retailer_id")
        private String retailerId;

        private String status;

        @JsonProperty("store_id")
        private String storeId;

        @JsonProperty("total_price")
        private double totalPrice;

        private List<ProductSC> products;

        @JsonProperty("delivery_prices")
        private DeliveryPriceSC deliveryPrices;
}
