package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SuggestionOfferV1 extends BaseObject {

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private Double price;

    @JsonSchema(required = true)
    private Double discount;

    @JsonSchema(required = true)
    @JsonProperty(value = "instamart_price")
    private Double instamartPrice;

    @JsonSchema(required = true)
    @JsonProperty(value = "retailer_id")
    private Integer retailerId;

    @JsonSchema(required = true)
    private SuggestionProductV1 product;
}
