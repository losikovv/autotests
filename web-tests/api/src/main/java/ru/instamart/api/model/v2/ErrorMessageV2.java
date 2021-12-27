package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorMessageV2 extends BaseObject {
    private String field;
    private String message;
    @JsonProperty(value = "human_message")
    private String humanMessage;
    private String detail;
}
