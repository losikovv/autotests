package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SessionV2 extends BaseObject {
    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty(value = "expires_at")
    private String expiresAt;
    @JsonProperty(value = "is_valid")
    private Boolean isValid;
}