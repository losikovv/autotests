package ru.instamart.api.response.v1.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import ru.instamart.api.model.v1.AdminOrderCancellationsV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderCancellationV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private AdminOrderCancellationsV1 cancellation;
}