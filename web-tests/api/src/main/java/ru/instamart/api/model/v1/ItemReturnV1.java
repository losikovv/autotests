package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class ItemReturnV1 extends BaseObject {
    @JsonSchema(required = true)
    private Integer position;
    @JsonSchema(required = true)
    @JsonProperty("line_item_uuid")
    private String lineItemUuid;
    @JsonSchema(required = true)
    private String name;
    @JsonSchema(required = true)
    private Double quantity;
    @JsonSchema(required = true)
    private Integer percent;
    @JsonSchema(required = true)
    private Double amount;
    @JsonSchema(required = true)
    @JsonProperty("amount_total")
    private Double amountTotal;
    @JsonSchema(required = true)
    private String kind;
    @Null
    @JsonSchema(required = true)
    @JsonProperty("price_type")
    private String priceType;
    @Null
    @JsonSchema(required = true)
    @JsonProperty("product_path")
    private String productPath;
}
