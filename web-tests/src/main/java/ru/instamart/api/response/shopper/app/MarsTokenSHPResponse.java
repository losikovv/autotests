package ru.instamart.api.response.shopper.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class MarsTokenSHPResponse extends BaseResponseObject {
    private String access_token;
    @JsonProperty(value = "expires_in")
    private Integer expiresIn;
}
