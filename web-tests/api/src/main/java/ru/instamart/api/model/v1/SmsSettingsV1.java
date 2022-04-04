
package ru.instamart.api.model.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SmsSettingsV1 extends BaseObject {

    @JsonSchema(required = true)
    private BaseV1 base;

    @JsonSchema(required = true)
    private InfobipV1 infobip;

    @JsonSchema(required = true)
    private SbertipsV1 sbertips;

    @JsonSchema(required = true)
    private SmsimpleV1 smsimple;
}
