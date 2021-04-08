package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorMessage extends BaseObject {
    private String field;
    private String message;
    @JsonProperty(value = "human_message")
    private String humanMessage;
}
