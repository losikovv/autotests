package instamart.api.objects.shopper;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Offer extends BaseObject {
    private Integer id;
    private String uuid;
    private Integer stock;
    @JsonProperty(value = "retailer_sku")
    private String retailerSku;
}
