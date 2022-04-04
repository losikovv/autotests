
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SmsimpleV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("sms_login")
    private String smsLogin;

    @JsonSchema(required = true)
    @JsonProperty("sms_or_id")
    private String smsOrId;

    @JsonSchema(required = true)
    @JsonProperty("sms_password")
    private String smsPassword;
}
