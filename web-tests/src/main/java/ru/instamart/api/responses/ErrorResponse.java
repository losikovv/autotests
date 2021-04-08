package ru.instamart.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.v2.ErrorMessageV2;
import ru.instamart.api.objects.v2.ErrorsV2;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorResponse extends BaseResponseObject {
    private String error;
    private ErrorsV2 errors;
    @JsonProperty(value = "error_messages")
    private List<ErrorMessageV2> errorMessages = null;
    private String message;
    private String code;
}
