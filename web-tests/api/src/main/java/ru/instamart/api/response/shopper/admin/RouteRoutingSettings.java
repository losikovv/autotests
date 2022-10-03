package ru.instamart.api.response.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.RoutingSettingsStoreV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class RouteRoutingSettings extends BaseResponseObject {
    @JsonSchema(required = true)
    private RoutingSettingsStoreV1 store = null;
}
