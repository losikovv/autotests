
package ru.instamart.api.response.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v1.DesktopV1;
import ru.instamart.api.model.v1.MobileV1;

@Data
@EqualsAndHashCode(callSuper=false)
public class PrimeCategoryV1 extends BaseObject {

    @JsonSchema(required = true)
    private DesktopV1 desktop;
    @JsonSchema(required = true)
    private MobileV1 mobile;
}
