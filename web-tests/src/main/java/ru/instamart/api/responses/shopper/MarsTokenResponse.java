package instamart.api.responses.shopper;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MarsTokenResponse extends BaseResponseObject {
    private String access_token;
    @JsonProperty(value = "expires_in")
    private Integer expiresIn;
}
