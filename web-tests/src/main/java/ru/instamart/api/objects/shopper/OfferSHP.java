package ru.instamart.api.objects.shopper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OfferSHP extends BaseObject {
    private Integer id;
    private String uuid;
    private Integer stock;
    @JsonProperty(value = "retailer_sku")
    private String retailerSku;
}
