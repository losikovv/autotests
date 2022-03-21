package ru.instamart.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.InvalidParamsV2;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ErrorTypeResponse extends BaseResponseObject {
    private String type;
    private String title;
    @JsonProperty("invalid-params")
    private List<InvalidParamsV2> invalidParams;
    private String detail;
    private int status;


}

