
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserConfigV1 extends BaseObject {

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    @JsonProperty("send_emails")
    private Boolean sendEmails;

    @JsonSchema(required = true)
    @JsonProperty("send_push")
    private Boolean sendPush;

    @JsonSchema(required = true)
    @JsonProperty("send_sms")
    private Boolean sendSms;

}
