package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorMessageV2 extends BaseObject {
    private String field;
    private String message;
    @JsonProperty(value = "human_message")
    private String humanMessage;
}
