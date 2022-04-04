
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class TimePreferencesV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("company_support_time_end")
    private String companySupportTimeEnd;

    @JsonSchema(required = true)
    @JsonProperty("company_support_time_start")
    private String companySupportTimeStart;
}
