package ru.instamart.api.response.shadowcat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PromocodeResponse extends BaseResponseObject {
    @JsonProperty(value = "active_until")
    private String activeUntil;

    @JsonSchema(required = true)
    private String code;

    @JsonProperty(value = "customer_id")
    private String customerId;

    @JsonProperty(value = "discount_amount")
    private int discountAmount;

    private int length;

    private int id;
}
