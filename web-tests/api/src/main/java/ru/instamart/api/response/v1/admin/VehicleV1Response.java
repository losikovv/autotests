package ru.instamart.api.response.v1.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.VehicleV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public final class VehicleV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private VehicleV1 vehicle;
}
