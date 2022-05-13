package ru.instamart.api.response.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.OperationalZoneDispatchSettingV1;
import ru.instamart.api.model.shopper.admin.StoreEstimatorSettingV1;
import ru.instamart.api.response.BaseResponseObject;
@Data
@EqualsAndHashCode(callSuper=false)
public class RouteEstimatorResponse extends BaseResponseObject {
    @JsonSchema(required = true)
    private StoreEstimatorSettingV1 storeEstimatorSetting;
}
