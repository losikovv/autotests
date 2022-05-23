package ru.instamart.api.response.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.StoreDispatchSettingsV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoreDispatchSettingsResponse extends BaseResponseObject {

    @JsonSchema(required = true)
    private StoreDispatchSettingsV1 storeDispatchSetting;

}