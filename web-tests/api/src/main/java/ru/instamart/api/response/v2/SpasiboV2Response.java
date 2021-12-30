
package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class SpasiboV2Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private Boolean available;

    @JsonSchema(required = true)
    private String info;

    @JsonSchema(required = true)
    @JsonProperty("min_bonus_amount")
    private Integer minBonusAmount;

    @JsonSchema(required = true)
    @JsonProperty("min_money_amount")
    private Integer minMoneyAmount;

    @JsonSchema(required = true)
    @JsonProperty("min_total")
    private Integer minTotal;
}
