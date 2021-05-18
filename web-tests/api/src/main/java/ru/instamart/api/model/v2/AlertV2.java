package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AlertV2 extends BaseObject {
    private String message;
    @JsonProperty(value = "full_message")
    private String fullMessage;
    private String type;
}
