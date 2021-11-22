package ru.instamart.api.response.v2;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderV2Response extends BaseResponseObject {
    @JsonSchema(required = true)
    private OrderV2 order;
}
