package ru.instamart.api.response.shopper.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OtpCodeSHPResponse extends BaseResponseObject {

    private String code;
    @JsonProperty("expires_at")
    private String expiresAt;

}
