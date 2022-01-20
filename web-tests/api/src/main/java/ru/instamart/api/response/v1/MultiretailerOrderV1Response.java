
package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.AddressV1;
import ru.instamart.api.model.v1.UserV1;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MultiretailerOrderV1Response extends BaseResponseObject  {

    @JsonSchema(required = true)
    @JsonProperty("adjustment_total")
    private Double adjustmentTotal;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("company_id")
    private Object companyId;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("completed_at")
    private Object completedAt;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("deliveries_info")
    private Object deliveriesInfo;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    @JsonProperty("item_count")
    private Long itemCount;

    @JsonSchema(required = true)
    @JsonProperty("item_total")
    private Double itemTotal;

    @JsonSchema(required = true)
    @JsonProperty("mnogoru_bonuses_text")
    private String mnogoruBonusesText;


    @JsonSchema(required = true)
    private String number;

    @JsonSchema(required = true)
    @JsonProperty("promo_total")
    private Double promoTotal;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("replacement_policy")
    private Object replacementPolicy;

    @JsonSchema(required = true)
    @JsonProperty("ship_address")
    private AddressV1 shipAddress;

    @JsonSchema(required = true)
    @JsonProperty("ship_total")
    private Double shipTotal;

    @JsonSchema(required = true)
    private List<Object> shipments;

    @JsonSchema(required = true)
    @JsonProperty("shipping_method_kind")
    private String shippingMethodKind;

    @JsonSchema(required = true)
    private String state;

    @JsonSchema(required = true)
    private String token;

    @JsonSchema(required = true)
    private Double total;

    @JsonSchema(required = true)
    private UserV1 user;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("vat_info")
    private Object vatInfo;
}
