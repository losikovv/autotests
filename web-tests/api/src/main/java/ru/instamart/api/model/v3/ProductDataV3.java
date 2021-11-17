package ru.instamart.api.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductDataV3 {
    @JsonProperty("retailer_sku")
    private Long retailerSku;
    private Long sku;
    private String name;
    private boolean available;


}
