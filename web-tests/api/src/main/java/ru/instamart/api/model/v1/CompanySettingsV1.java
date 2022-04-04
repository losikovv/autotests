
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanySettingsV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("people_preferences")
    private PeoplePreferencesV1 peoplePreferences;

    @JsonSchema(required = true)
    private CompanyPreferencesV1 preferences;

    @JsonSchema(required = true)
    @JsonProperty("time_preferences")
    private TimePreferencesV1 timePreferences;
}
