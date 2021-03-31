package instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Alert extends BaseObject {
    private String message;
    @JsonProperty(value = "full_message")
    private String fullMessage;
    private String type;
}
