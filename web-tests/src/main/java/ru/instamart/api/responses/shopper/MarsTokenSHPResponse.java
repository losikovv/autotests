package ru.instamart.api.responses.shopper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class MarsTokenSHPResponse extends BaseResponseObject {
    private String access_token;
    @JsonProperty(value = "expires_in")
    private Integer expiresIn;
}
