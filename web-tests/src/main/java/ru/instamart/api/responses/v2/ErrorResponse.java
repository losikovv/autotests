package instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.ErrorMessage;
import instamart.api.objects.v2.Errors;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorResponse extends BaseResponseObject {
    private String error;
    private Errors errors;
    @JsonProperty(value = "error_messages")
    private List<ErrorMessage> errorMessages = null;
}
