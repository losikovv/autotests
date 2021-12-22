
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PreferencesV1 extends BaseObject {
    private Integer days;
    @JsonProperty("time_gap")
    private Integer timeGap;
    private Integer time;
    private Integer number;
}