
package ru.instamart.api.response.v3;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v3.CheckoutOrderV3;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderV3Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private CheckoutOrderV3 order;
}
