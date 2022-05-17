package ru.instamart.api.response.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.StoreOrderServiceSettingV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class RouteOrderServiceSettings extends BaseResponseObject {
    @JsonSchema(required = true)
    private StoreOrderServiceSettingV1 storeOrderServiceSetting = null;
}
