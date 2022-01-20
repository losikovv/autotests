
package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SberPrimeBannersWithoutSubscriptionV1Response extends BaseResponseObject {

    @JsonSchema
    @JsonProperty("prime_category_without_subscription")
    private PrimeCategoryV1 primeCategoryWithoutSubscription;
}
