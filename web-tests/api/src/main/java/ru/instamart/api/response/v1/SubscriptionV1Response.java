
package ru.instamart.api.response.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.SubscriptionV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SubscriptionV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private SubscriptionV1 subscription;
}
